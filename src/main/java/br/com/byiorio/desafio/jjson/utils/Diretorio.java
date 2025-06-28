package br.com.byiorio.desafio.jjson.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Diretorio {

    public static void criar(String caminho) {
        try {
            Path path = Paths.get(caminho);
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new JpaJsonException("Erro ao criar diretorio" + caminho);
        }
    }

    public static boolean verifica(String caminho) {
        Path path = Paths.get(caminho);
        return Files.exists(path) && Files.isDirectory(path);
    }
}
