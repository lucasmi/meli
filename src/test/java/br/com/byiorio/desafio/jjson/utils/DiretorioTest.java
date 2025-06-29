package br.com.byiorio.desafio.jjson.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DiretorioTest {

    private final String caminho = "src/test/dbtemp/diretorioTeste";
    private final String caminhoArquivo = "src/test/dbtemp/diretorioTeste/arquivo.json";

    @AfterEach
    void cleanup() throws IOException {
        Diretorio.apagar("src/test/dbtemp/diretorioTeste");
    }

    @Test
    void testCriar() {
        Diretorio.criar(caminho);
        assertEquals(true, Diretorio.verifica(caminho));
    }

    @Test
    void testVerificaDiretorioInexistente() {
        String caminhoInexistente = "src/test/dbtemp/naoExiste";
        assertEquals(false, Diretorio.verifica(caminhoInexistente));
    }

    @Test
    void testListaArquivos() throws IOException {
        Diretorio.criar(caminho);
        Files.createFile(Path.of(caminhoArquivo));
        List<String> arquivos = Diretorio.listaArquivos(caminho);
        assertEquals(1, arquivos.size());
        assertEquals("arquivo", arquivos.get(0));
    }

    @Test
    void testApagarDiretorio() throws IOException {
        Diretorio.criar(caminho);
        Files.createFile(Path.of(caminhoArquivo));
        Diretorio.apagar(caminho);
        assertEquals(false, Diretorio.verifica(caminho));
    }

    @Test
    void testCriarThrowsException() {
        // Tenta criar um diretório em um caminho inválido para forçar uma exceção
        String caminhoInvalido = "?:/diretorioInvalido";
        Exception exception = assertThrows(InvalidPathException.class, () -> {
            Diretorio.criar(caminhoInvalido);
        });
        assertEquals(true, exception.getMessage().contains("Illegal"));
    }
}
