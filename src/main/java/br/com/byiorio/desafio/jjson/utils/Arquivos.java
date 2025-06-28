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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import br.com.byiorio.desafio.jjson.exceptions.JsonJpaException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Arquivos {

    private static final Lock lock = new ReentrantLock();
    private static Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    public static boolean verifica(String caminho) {
        return Files.isRegularFile(Path.of(caminho));
    }

    public static void salvar(String caminho, Object objeto) {
        lock.lock();
        try (FileWriter writer = new FileWriter(caminho, false)) {
            gson.toJson(objeto, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static <T> T ler(String path, Class<T> clazz) {
        lock.lock();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path))) {
            return gson.fromJson(reader, clazz);
        } catch (IOException | JsonSyntaxException e) {
            throw new JsonJpaException("Erro ao ler o JSON no arquivo " + path);
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
            throw new JsonJpaException("Erro ao apagar JSON no caminho " + caminho);
        } finally {
            lock.unlock();
        }
    }
}
