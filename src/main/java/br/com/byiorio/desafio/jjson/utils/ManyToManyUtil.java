package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.ManyToMany;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.repository.IJpaJsonRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@SuppressWarnings("unchecked")
public class ManyToManyUtil {
    public static void realizaRelacionamentos(IJapJsonEntity clazz, String acao) {
        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToMany.class)) {
                ReflectionUtils.makeAccessible(field);
                try {
                    // Carrega parametros da anotacao
                    ManyToMany otm = field.getAnnotation(ManyToMany.class);

                    // Pega o id da tabela de destino
                    Field idFieldDestino = clazz.getClass().getDeclaredField(field.getName());
                    ReflectionUtils.makeAccessible(idFieldDestino);

                    // Pega o id da tabela de origem
                    Field idFieldOrigem = clazz.getClass().getDeclaredField("id");
                    ReflectionUtils.makeAccessible(idFieldOrigem);
                    String idPkOrigem = (String) idFieldOrigem.get(clazz);

                    // Se for HashSet<String>, não faz nada, Seria a tabela de relacionamento que
                    if (idFieldDestino.getType().equals(HashSet.class)) {
                        // Carrega o repository e a entidade de origem para pegar os ids
                        IJpaJsonRepository<IJapJsonEntity> repositorioOrigem = SpringContext
                                .getBean(otm.repositorySource());
                        IJapJsonEntity entidadeDestino = repositorioOrigem.buscar(idPkOrigem, otm.entitySource());

                        // Salva toda a coleção de ids no objeto atual
                        idFieldDestino.set(clazz, idFieldDestino.get(entidadeDestino));
                        continue; // pula para o próximo campo
                    }

                    // Pega os ids
                    String idDestino = (String) idFieldDestino.get(clazz);

                    // Carrega o repository e a entidade
                    IJpaJsonRepository<IJapJsonEntity> repositorioDestino = SpringContext
                            .getBean(otm.repositoryTarget());
                    IJapJsonEntity entidadeDestino = repositorioDestino.buscar(idDestino, otm.entityTarget());

                    // Pega a lista de relacionamentos
                    Field listaHashSetRelacionamento = entidadeDestino.getClass().getDeclaredField(otm.mappedBy());
                    ReflectionUtils.makeAccessible(listaHashSetRelacionamento);
                    HashSet<String> listaIdsTabelaDestino = (HashSet<String>) listaHashSetRelacionamento
                            .get(entidadeDestino);

                    // Apaga ou adiciona conforme a acao
                    if (acao.equals("inserir")) {
                        listaIdsTabelaDestino.add(idPkOrigem);
                    } else if (acao.equals("remover")) {
                        listaIdsTabelaDestino.remove(idPkOrigem);
                    }

                    // Salva tudo
                    repositorioDestino.salvar(entidadeDestino);

                } catch (IllegalAccessException | IllegalArgumentException | SecurityException
                        | NoSuchFieldException e) {
                    throw new JpaJsonException(e.getMessage());
                }
            }
        }
    }

    public static void apagarRelacionados(IJapJsonEntity clazz) {
        realizaRelacionamentos(clazz, "remover");
    }

    // fazer metodo para inserir relacionamentos
    public static void inserirRelacionamentos(IJapJsonEntity clazz) {
        realizaRelacionamentos(clazz, "inserir");
    }

}