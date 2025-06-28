package br.com.byiorio.desafio.jjson.utils;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

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
            throw new RuntimeException("Erro ao ler o JSON com Gson", e);
        } finally {
            lock.unlock();
        }
    }
}
