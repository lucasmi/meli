package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import br.com.byiorio.desafio.jjson.annotations.ID;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class IdGeneratorUtil {
    public static void processIdAnnotations(Object entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {

                if (field.getType() != String.class) {
                    throw new IllegalArgumentException("O campo anotado com @ID deve ser do tipo String.");
                }

                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    if (value == null || value.toString().isEmpty()) {
                        String uuid = UUID.randomUUID().toString();
                        String encoded = URLEncoder.encode(uuid, StandardCharsets.UTF_8);
                        field.set(entity, encoded);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Erro ao gerar ID", e);
                }
            }
        }
    }
}