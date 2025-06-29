package br.com.byiorio.desafio.jjson.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;

class ArquivosTest {
    @Test
    void testLerArquivoInexistente() {
        assertThrows(JpaJsonException.class, () -> {
            Arquivos.ler("src/test/dbtemp/inexistente.json", Object.class);
        });
    }

    @Test
    void testApagarArquivoInexistente() {
        JpaJsonException exception = assertThrows(JpaJsonException.class, () -> {
            Arquivos.apagar("src/test/dbtemp/inexistente.json");
        });
        assertTrue(
                exception.getDescricao().contains("Erro ao apagar JSON no caminho src/test/dbtemp/inexistente.json"));
    }
}
