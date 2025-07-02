package br.com.byiorio.desafio.jjson.utils;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.springframework.util.ReflectionUtils;

import br.com.byiorio.desafio.jjson.annotations.ManyToOne;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.repository.IJpaJsonRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@SuppressWarnings("unchecked")
public class ManyToOneUtil {
    public static void realizaRelacionamentos(IJapJsonEntity clazz, String acao) {
        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                ReflectionUtils.makeAccessible(field);
                try {
                    // Carrega parametros da anotacao
                    ManyToOne otm = field.getAnnotation(ManyToOne.class);

                    // Pega o id da tabela de destino
                    Field idField = clazz.getClass().getDeclaredField(field.getName());
                    ReflectionUtils.makeAccessible(idField);
                    String idPkDestino = (String) idField.get(clazz);

                    // Pega o id da tabela de origem
                    Field idFieldOrigem = clazz.getClass().getDeclaredField("id");
                    ReflectionUtils.makeAccessible(idFieldOrigem);
                    String idPkOrigem = (String) idFieldOrigem.get(clazz);

                    // Carrega o repository e a entidade
                    IJpaJsonRepository<IJapJsonEntity> repositorioDestino = SpringContext.getBean(otm.repository());
                    IJapJsonEntity tabelaDestino = repositorioDestino.buscar(idPkDestino, otm.entity());

                    // Pega a lista de relacionamentos
                    Field listaHashSetRelacionamento = tabelaDestino.getClass().getDeclaredField(otm.mappedBy());
                    ReflectionUtils.makeAccessible(listaHashSetRelacionamento);
                    HashSet<String> listaIdsTabelaDestino = (HashSet<String>) listaHashSetRelacionamento
                            .get(tabelaDestino);

                    // Apaga ou adiciona conforme a acao
                    if (acao.equals("inserir")) {
                        listaIdsTabelaDestino.add(idPkOrigem);
                    } else if (acao.equals("remover")) {
                        listaIdsTabelaDestino.remove(idPkOrigem);
                    }

                    // Salva tudo
                    repositorioDestino.salvar(tabelaDestino);

                } catch (IllegalAccessException | IllegalArgumentException | SecurityException
                        | NoSuchFieldException e) {
                    throw new JpaJsonException(e.getMessage());
                }
            }
        }
    }

    public static void apagarRelacionados(IJapJsonEntity clazz) {
        realizaRelacionamentos(clazz, "remover");
    }

    // fazer metodo para inserir relacionamentos
    public static void inserirRelacionamentos(IJapJsonEntity clazz) {
        realizaRelacionamentos(clazz, "inserir");
    }

}