package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.GeneratedValue;
import br.com.byiorio.desafio.jjson.annotations.Id;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class IdGeneratorUtil {
    public static void processIdAnnotations(Object entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {

                if (field.getType() != String.class) {
                    throw new IllegalArgumentException("O campo anotado com @ID deve ser do tipo String.");
                }

                ReflectionUtils.makeAccessible(field);
                try {
                    // Verifica se o campo possui a anotação @GeneratedValue
                    if (field.isAnnotationPresent(GeneratedValue.class)) {
                        Object value = field.get(entity);
                        if ((value == null || value.toString().isEmpty())
                                && field.getAnnotation(GeneratedValue.class).strategy()
                                        .equals(GeneratedValue.Strategy.UUID)) {
                            field.set(entity, getUUid()); // NOSONAR
                        }

                    } else {
                        throw new JpaJsonException("O campo anotado com @Id deve ter um @GeneratedValue.");
                    }

                } catch (IllegalAccessException e) {
                    throw new JpaJsonException("Erro ao gerar ID");
                }
            }
        }
    }

    public static String getUUid() {
        String uuid = UUID.randomUUID().toString();
        return URLEncoder.encode(uuid, StandardCharsets.UTF_8);
    }
}