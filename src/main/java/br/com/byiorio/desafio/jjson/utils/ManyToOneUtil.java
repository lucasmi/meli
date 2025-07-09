package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.ManyToOne;
import br.com.byiorio.desafio.jjson.entity.EstadoEnum;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.repository.IJpaJsonRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@SuppressWarnings("unchecked")
public class ManyToOneUtil {
    private static final String REMOVER = "remover";
    private static final String INSERIR = "inserir";
    private static final String ATUALIZAR_REMOVER = "atualizar_remover";

    public static void realizaRelacionamentos(IJapJsonEntity clazz, String acao) {
        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                try {
                    // Carrega parametros da anotacao
                    ReflectionUtils.makeAccessible(field);
                    ManyToOne otm = field.getAnnotation(ManyToOne.class);

                    // Pega o id da tabela de destino
                    Field idFieldDestino = clazz.getClass().getDeclaredField(field.getName());
                    ReflectionUtils.makeAccessible(idFieldDestino);
                    String idPkDestino = (String) idFieldDestino.get(clazz);

                    // Pega o id da tabela de origem
                    Field idFieldOrigem = clazz.getClass().getDeclaredField("id");
                    ReflectionUtils.makeAccessible(idFieldOrigem);
                    String idPkOrigem = (String) idFieldOrigem.get(clazz);

                    // Carrega o repository e a entidade
                    // Log do processo de deleção
                    log.info("realizaRelacionamentos entidade: {} id: {} acao: {}", otm.entityTarget().getSimpleName(),
                            idPkDestino, acao);

                    // Precisa saber valor atual para saber se foi removido o desconto
                    if (idPkDestino == null) {
                        IJpaJsonRepository<IJapJsonEntity> repositorioOrigemOriginal = SpringContext
                                .getBean(otm.repositorySource());
                        IJapJsonEntity entidadeOrigemOriginal = repositorioOrigemOriginal.buscar(idPkOrigem,
                                otm.entitySource());

                        String valorCampoOrigemOriginal = (String) field.get(entidadeOrigemOriginal);
                        if (valorCampoOrigemOriginal != null) {
                            idPkDestino = valorCampoOrigemOriginal;
                            acao = ATUALIZAR_REMOVER;
                        } else {
                            continue;
                        }
                    }

                    // Verifica entidade destino
                    IJpaJsonRepository<IJapJsonEntity> repositorioDestino = SpringContext
                            .getBean(otm.repositoryTarget());
                    IJapJsonEntity entidadeDestino = repositorioDestino.buscar(idPkDestino, otm.entityTarget());

                    // Pega a lista de relacionamentos
                    Field listaHashSetRelacionamento = entidadeDestino.getClass().getDeclaredField(otm.mappedBy());
                    ReflectionUtils.makeAccessible(listaHashSetRelacionamento);
                    HashSet<String> listaIdsTabelaDestino = (HashSet<String>) listaHashSetRelacionamento
                            .get(entidadeDestino);

                    // Apaga ou adiciona conforme a acao
                    if (acao.equals(INSERIR) || acao.equals(ATUALIZAR_REMOVER)) {
                        if (acao.equals(ATUALIZAR_REMOVER)) {
                            listaIdsTabelaDestino.remove(idPkOrigem);
                        } else {
                            listaIdsTabelaDestino.add(idPkOrigem);
                        }

                        repositorioDestino.alteraEstado(entidadeDestino, EstadoEnum.ATUALIZAR);

                    } else if (acao.equals(REMOVER)) {
                        listaIdsTabelaDestino.remove(idPkOrigem);
                        repositorioDestino.alteraEstado(entidadeDestino, EstadoEnum.REMOVER);
                    }

                } catch (IllegalAccessException | IllegalArgumentException | SecurityException
                        | NoSuchFieldException e) {
                    throw new JpaJsonException(e.getMessage());
                }
            }
        }
    }

    public static void apagarRelacionados(IJapJsonEntity clazz) {
        realizaRelacionamentos(clazz, REMOVER);
    }

    // fazer metodo para inserir relacionamentos
    public static void salvarRelacionamento(IJapJsonEntity clazz) {
        realizaRelacionamentos(clazz, INSERIR);
    }

}