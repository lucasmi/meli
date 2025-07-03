package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.OneToMany;
import br.com.byiorio.desafio.jjson.entity.EstadoEnum;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.repository.IJpaJsonRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OneToManyUtil {

    @SuppressWarnings("unchecked")
    public static void salvarRelacionamento(EstadoEnum estado, IJapJsonEntity clazz, boolean insert) { // NOSONAR
        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToMany.class)) {
                ReflectionUtils.makeAccessible(field);
                try {
                    // Carrega parametros da anotacao
                    OneToMany otm = field.getAnnotation(OneToMany.class);

                    // Pega o id da tabela de destino
                    Field idFieldDestino = clazz.getClass().getDeclaredField(field.getName());
                    ReflectionUtils.makeAccessible(idFieldDestino);

                    // Pega o id da tabela de origem
                    Field idFieldOrigem = clazz.getClass().getDeclaredField("id");
                    ReflectionUtils.makeAccessible(idFieldOrigem);
                    String idPkOrigem = (String) idFieldOrigem.get(clazz);

                    // Se for HashSet<String>, não faz nada, Seria a tabela de relacionamento que
                    if (idFieldDestino.getType().equals(HashSet.class) && !insert) {

                        // Log do processo de deleção
                        log.info("salvarRelacionamento field: {} entidade: {} id: {} estado: {} ",
                                idFieldDestino.getName(),
                                otm.entitySource().getSimpleName(),
                                idPkOrigem,
                                estado);

                        // Carrega o repository e a entidade de origem para pegar os ids
                        IJpaJsonRepository<IJapJsonEntity> repositorioOrigem = SpringContext
                                .getBean(otm.repositorySource());
                        IJapJsonEntity entidadeOrigem = repositorioOrigem.buscar(idPkOrigem,
                                otm.entitySource());

                        Field fieldOrigem = entidadeOrigem.getClass().getDeclaredField(field.getName());
                        ReflectionUtils.makeAccessible(fieldOrigem);

                        HashSet<String> origemSet = (HashSet<String>) fieldOrigem.get(entidadeOrigem);
                        HashSet<String> destinoSet = (HashSet<String>) idFieldDestino.get(clazz);

                        if (estado == null) {
                            idFieldDestino.set(clazz, origemSet); // NOSONAR
                        } else if (EstadoEnum.REMOVER == estado || EstadoEnum.ATUALIZAR == estado) {
                            idFieldDestino.set(clazz, destinoSet); // NOSONAR
                        }

                    }

                } catch (IllegalAccessException | IllegalArgumentException | SecurityException
                        | NoSuchFieldException e) {
                    throw new JpaJsonException(e.getMessage());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void apagarRelacionados(IJapJsonEntity clazz, EstadoEnum estado) {
        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToMany.class)) {
                ReflectionUtils.makeAccessible(field);
                try {
                    OneToMany otm = field.getAnnotation(OneToMany.class);

                    IJpaJsonRepository<?> tabelaDestino = SpringContext.getBean(otm.repositoryTarget());

                    HashSet<String> listaHashSetRelacionamento = (HashSet<String>) field.get(clazz);
                    for (String idPkDestino : listaHashSetRelacionamento) {
                        // Log do processo de deleção
                        log.info("apagarRelacionados field: {} entidade: {} id: {} estado: {} ",
                                field.getName(),
                                otm.entitySource().getSimpleName(),
                                idPkDestino,
                                estado);

                        ((IJpaJsonRepository<IJapJsonEntity>) tabelaDestino).removeEstado(idPkDestino,
                                otm.entityTarget(), EstadoEnum.REMOVER);
                    }
                } catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
                    throw new JpaJsonException(e.getMessage());
                }
            }
        }
    }
}