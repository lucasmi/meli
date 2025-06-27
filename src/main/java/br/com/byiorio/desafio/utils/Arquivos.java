package br.com.byiorio.desafio.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class Arquivos {

    public static boolean verifica(String caminho) {
        return Files.isRegularFile(Path.of(caminho));
    }
}
