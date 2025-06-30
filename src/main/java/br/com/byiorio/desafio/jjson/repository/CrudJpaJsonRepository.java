package br.com.byiorio.desafio.jjson.repository;

import java.util.LinkedList;
import java.util.List;

import br.com.byiorio.desafio.jjson.config.JpajsonConfig;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.utils.Arquivos;
import br.com.byiorio.desafio.jjson.utils.Diretorio;
import br.com.byiorio.desafio.jjson.utils.IdGeneratorUtil;

public abstract class CrudJpaJsonRepository implements IAcoesBasicas, IJpaJsonRepository<IJapJsonEntity> {
    private static final String JSON_EXTENSAO = ".json";

    JpajsonConfig jpajsonConfig;

    protected CrudJpaJsonRepository(JpajsonConfig jpajsonConfig) {
        this.jpajsonConfig = jpajsonConfig;
        this.configurar();
    }

    @Override
    public void configurar() {
        // Cria diretorio onde serao armazenados os Jsons
        String caminhoCompleto = this.jpajsonConfig.getNome().concat("/").concat(getNome());

        // Verifica se o diretorio existe, se nao existir cria
        if (!Diretorio.verifica(caminhoCompleto)) {
            Diretorio.criar(caminhoCompleto);
        }
    }

    @Override
    public <E extends IJapJsonEntity> E salvar(E entidade) {
        boolean novoArquivo = false;

        // Verifica se o id da entidade é nulo, se for, gera um novo id
        if (entidade.getId() == null) {
            // Se o id for nulo, gera um novo id
            IdGeneratorUtil.processIdAnnotations(entidade);
            novoArquivo = true;
        }

        // Verifica se o id da entidade é vazio, se for, gera um novo arquivo
        String caminhoArquivo = jpajsonConfig.getNome().concat("/").concat(getNome()).concat("/")
                .concat(entidade.getId())
                .concat(JSON_EXTENSAO);

        if (!Arquivos.verifica(caminhoArquivo) && novoArquivo) {
            // Se for arquivo novo salva
            Arquivos.salvar(caminhoArquivo, entidade);

        } else if (Arquivos.verifica(caminhoArquivo) && !novoArquivo) {
            // Se for arquivo antigo, verifica se existe
            Arquivos.salvar(caminhoArquivo, entidade);
        } else {
            throw new JpaJsonException("Erro ao salvar arquivo no caminho " + caminhoArquivo);
        }

        return entidade;
    }

    public <E extends IJapJsonEntity> E salvar(String id, E entidade) {
        entidade.setId(id);
        return salvar(entidade);
    }

    @Override
    public <E extends IJapJsonEntity> E buscar(String id, Class<E> clazz) {
        // Gera caminhho
        String caminhoArquivo = jpajsonConfig.getNome().concat("/").concat(getNome()).concat("/").concat(id)
                .concat(JSON_EXTENSAO);

        // Le arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            return Arquivos.ler(caminhoArquivo, clazz);
        } else {
            throw new JpaJsonException("o id " + id + " nao foi encontrado na base " + getNome());
        }

    }

    public void apagar(String id) {
        // gera caminhho
        String caminhoArquivo = jpajsonConfig.getNome().concat("/").concat(getNome()).concat("/").concat(id)
                .concat(JSON_EXTENSAO);

        // Apagar Arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            Arquivos.apagar(caminhoArquivo);
        }
    }

    @Override
    public <E extends IJapJsonEntity> List<E> buscarTodos(Class<E> clazz) {
        // Pega todos os arquivos
        LinkedList<String> todosArquivos = new LinkedList<>(
                Diretorio.listaArquivos(jpajsonConfig.getNome().concat("/").concat(getNome())));

        // Consulta todos os arquivos e adiciona na lista
        LinkedList<E> entidades = new LinkedList<>();
        todosArquivos.forEach(id -> entidades.add(this.buscar(id, clazz)));

        return entidades;
    }

}