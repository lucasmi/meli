package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.ManyToOne;
import br.com.byiorio.desafio.jjson.entity.EstadoEnum;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.model.DestinoDTO;
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
                    // Variavel de valor antigo para controle
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

                        try {
                            IJapJsonEntity entidadeOrigemOriginal = repositorioOrigemOriginal.buscar(idPkOrigem,
                                    otm.entitySource());

                            String valorCampoOrigemOriginal = (String) field.get(entidadeOrigemOriginal);
                            if (valorCampoOrigemOriginal != null) {
                                if (idPkDestino == null) {
                                    // Apaga o relacionamento no destino
                                    executaAcao(idPkOrigem, REMOVER,
                                            retornaListaDestino(valorCampoOrigemOriginal, otm));
                                }

                                // Apaga o relacionamento no destino
                                // executaAcao(valorCampoOrigemOriginal, ATUALIZAR_REMOVER,
                                // retornaListaDestino(idPkDestino, otm));
                            }

                        } catch (JpaJsonException e) {
                            // Se nao achar um id antigo e for a primeira vez
                            continue;
                        }

                        // Verifica se ouve uma troca de ID e o sistema tem que remover do ID anterior
                    } else {
                        IJpaJsonRepository<IJapJsonEntity> repositorioOrigemOriginal = SpringContext
                                .getBean(otm.repositorySource());

                        try {
                            IJapJsonEntity entidadeOrigemOriginal = repositorioOrigemOriginal.buscar(idPkOrigem,
                                    otm.entitySource());

                            String valorCampoOrigemOriginal = (String) field.get(entidadeOrigemOriginal);
                            if (valorCampoOrigemOriginal != null && !valorCampoOrigemOriginal.equals(idPkDestino)) {
                                // Apaga o relacionamento antigo no destino
                                executaAcao(idPkOrigem, REMOVER,
                                        retornaListaDestino(valorCampoOrigemOriginal, otm));
                            }

                            // Atualiza o relacionamento no destino
                            executaAcao(idPkOrigem, acao,
                                    retornaListaDestino(idPkDestino, otm));
                            continue;

                        } catch (JpaJsonException e) {
                            // Fazer uma inserção nova
                            DestinoDTO destinoDTO = retornaListaDestino(idPkDestino, otm);
                            executaAcao(idPkOrigem, acao, destinoDTO);

                            // Se nao achar um id antigo e for a primeira vez
                            continue;
                        }
                    }

                    // Pega a lista do relacionamento destino
                    // DestinoDTO destinoDTO = retornaListaDestino(idPkDestino, otm);
                    // executaAcao(idPkOrigem, acao, destinoDTO);

                } catch (IllegalAccessException | IllegalArgumentException | SecurityException
                        | NoSuchFieldException e) {
                    throw new JpaJsonException(e.getMessage());
                }
            }
        }
    }

    private static void executaAcao(String idPkOrigem, String acao, DestinoDTO destinoDTO) {
        // Apaga ou adiciona conforme a acao
        if (acao.equals(INSERIR) || acao.equals(ATUALIZAR_REMOVER)) {
            // Atualiza destino com a FK que foi alterada
            if (acao.equals(ATUALIZAR_REMOVER)) {
                destinoDTO.getListaIdsTabelaDestino().remove(idPkOrigem);
            } else {
                destinoDTO.getListaIdsTabelaDestino().add(idPkOrigem);
            }

            destinoDTO.getRepositorioDestino().alteraEstado(destinoDTO.getEntidadeDestino(), EstadoEnum.ATUALIZAR);

        } else if (acao.equals(REMOVER)) {
            // Remove no destino a FK que foi alterada
            destinoDTO.getListaIdsTabelaDestino().remove(idPkOrigem);
            destinoDTO.getRepositorioDestino().alteraEstado(destinoDTO.getEntidadeDestino(), EstadoEnum.REMOVER);
        }
    }

    private static DestinoDTO retornaListaDestino(String id, ManyToOne otm)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // Nao retorna uma entidade em caso de null
        if (id == null) {
            return null;
        }

        // Verifica entidade destino
        IJpaJsonRepository<IJapJsonEntity> repositorioDestino = SpringContext
                .getBean(otm.repositoryTarget());
        IJapJsonEntity entidadeDestino = repositorioDestino.buscar(id, otm.entityTarget());

        // Pega a lista de relacionamentos
        Field listaHashSetRelacionamento = entidadeDestino.getClass().getDeclaredField(otm.mappedBy());
        ReflectionUtils.makeAccessible(listaHashSetRelacionamento);

        return DestinoDTO.builder()
                .idPkDestino(id)
                .entidadeDestino(entidadeDestino)
                .listaIdsTabelaDestino((HashSet<String>) listaHashSetRelacionamento.get(entidadeDestino))
                .repositorioDestino(repositorioDestino)
                .build();

    }

    public static void apagarRelacionados(IJapJsonEntity clazz) {
        realizaRelacionamentos(clazz, REMOVER);
    }

    // fazer metodo para inserir relacionamentos
    public static void salvarRelacionamento(IJapJsonEntity clazz) {
        realizaRelacionamentos(clazz, INSERIR);
    }

}