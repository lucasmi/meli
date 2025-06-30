package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.OneToMany;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.repository.IJpaJsonRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OneToManyUtil {
    @SuppressWarnings("unchecked")
    public static void apagarRelacionados(IJapJsonEntity clazz) {
        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToMany.class)) {
                ReflectionUtils.makeAccessible(field);
                try {
                    OneToMany otm = field.getAnnotation(OneToMany.class);

                    IJpaJsonRepository<?> tabelaDestino = SpringContext.getBean(otm.repository());

                    HashSet<String> listaHashSetRelacionamento = (HashSet<String>) field.get(clazz);
                    for (String idPkDestino : listaHashSetRelacionamento) {
                        ((IJpaJsonRepository<IJapJsonEntity>) tabelaDestino).apagar(idPkDestino, otm.entity());
                    }
                } catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
                    throw new JpaJsonException(e.getMessage());
                }
            }
        }
    }
}