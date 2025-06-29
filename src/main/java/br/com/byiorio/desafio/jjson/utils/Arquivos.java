package br.com.byiorio.desafio.jjson.utils;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Arquivos {

    private static final Lock lock = new ReentrantLock();

    public static boolean verifica(String caminho) {
        return Files.isRegularFile(Path.of(caminho));
    }

    public static void salvar(String caminho, Object objeto) {
        lock.lock();
        try (FileWriter writer = new FileWriter(caminho, false)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.writeValue(writer, objeto);
        } catch (IOException e) {
            throw new JpaJsonException("Erro ao salvar o JSON no caminho " + caminho);
        } finally {
            lock.unlock();
        }
    }

    public static <T> T ler(String path, Class<T> clazz) {
        lock.lock();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path))) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return mapper.readValue(reader, clazz);

        } catch (IOException e) {
            throw new JpaJsonException("Erro ao ler o JSON no arquivo " + path);

        } finally {
            lock.unlock();
        }
    }

    public static void apagar(String caminho) {
        lock.lock();
        try {
            Path caminhoFisico = Paths.get(caminho);
            Files.delete(caminhoFisico);
        } catch (IOException e) {
            throw new JpaJsonException("Erro ao apagar JSON no caminho " + caminho);
        } finally {
            lock.unlock();
        }
    }

    public static void copiar(String caminhoOrigem, String caminhoDestino) {
        lock.lock();
        try {
            Files.copy(
                    Paths.get(caminhoOrigem),
                    Paths.get(caminhoDestino));
        } catch (IOException e) {
            throw new JpaJsonException("Erro ao copiar " + caminhoOrigem + " para " + caminhoDestino);
        } finally {
            lock.unlock();
        }
    }

}
