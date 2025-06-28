package br.com.byiorio.desafio.jjson.utils;

public class StringUtils {
    public static String limpa(String texto) {
        if (texto != null) {
            return texto.replaceAll("[^a-zA-Z0-9]", "");
        }
        return null;
    }
}
