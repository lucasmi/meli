package br.com.byiorio.desafio.jjson.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

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

    public static List<String> listaArquivos(String caminho) {
        LinkedList<String> arquivos = new LinkedList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(caminho))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> arquivos.add(path.getFileName().toString().replaceAll(".json", "")));

        } catch (IOException e) {
            throw new JpaJsonException("Erro ao listar diretorio" + caminho);
        }

        return arquivos;
    }

    public static void apagar(String caminho) throws IOException {
        Path caminhoDiretorio = Paths.get(caminho);
        if (!Files.exists(caminhoDiretorio))
            return;

        Files.walkFileTree(caminhoDiretorio, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });

    }
}
