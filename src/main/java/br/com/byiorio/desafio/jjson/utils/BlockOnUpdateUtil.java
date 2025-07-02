package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.ManyToOne;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.repository.IJpaJsonRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class BlockOnUpdateUtil {
    public static void verificaRelacionamento(IJapJsonEntity clazz) {
        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                ReflectionUtils.makeAccessible(field);
                try {
                    // Carrega parametros da anotacao
                    ManyToOne otm = field.getAnnotation(ManyToOne.class);

                    if (otm.blockOnUpdateOf().equals(ManyToOne.None.class)) {
                        continue; // Se não tiver anotação de bloqueio, pula
                    }

                    // Pega o id da tabela de destino
                    Field idField = clazz.getClass().getDeclaredField(field.getName());
                    ReflectionUtils.makeAccessible(idField);
                    String idFk = (String) idField.get(clazz);

                    // Pega o id da tabela de origem
                    Field idFieldOrigem = clazz.getClass().getDeclaredField("id");
                    ReflectionUtils.makeAccessible(idFieldOrigem);
                    String idPk = (String) idFieldOrigem.get(clazz);

                    // Carrega o repository e a entidade
                    IJpaJsonRepository<IJapJsonEntity> repositorioOrigem = SpringContext.getBean(otm.blockOnUpdateOf());
                    IJapJsonEntity entidadeOrigemEntity = repositorioOrigem.buscar(idPk, clazz.getClass());

                    // Se o valor do idFK for direfente do valor do idFKOriginal,
                    // significa que o relacionamento foi alterado
                    Field idFKOriginal = entidadeOrigemEntity.getClass().getDeclaredField(field.getName());
                    ReflectionUtils.makeAccessible(idFKOriginal);
                    String idFkOriginal = (String) idFKOriginal.get(entidadeOrigemEntity);
                    if (idFk != null && !idFk.equals(idFkOriginal)) {
                        throw new JpaJsonException("O relacionamento com a entidade " + otm.entity().getSimpleName()
                                + " nao pode ser alterado");
                    }

                } catch (IllegalAccessException | IllegalArgumentException | SecurityException
                        | NoSuchFieldException e) {
                    throw new JpaJsonException(e.getMessage());
                }
            }
        }
    }
}