package br.com.byiorio.desafio.jjson.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Diretorio {

    public static void criar(String caminho) throws IOException {
        try {
            Path path = Paths.get(caminho);
            Files.createDirectories(path);
            System.out.println("Diretório criado em: " + path.toAbsolutePath());
        } catch (IOException e) {
            throw e;
        }
    }

    public static boolean verifica(String caminho) {
        Path path = Paths.get(caminho);
        return Files.exists(path) && Files.isDirectory(path);
    }
}
