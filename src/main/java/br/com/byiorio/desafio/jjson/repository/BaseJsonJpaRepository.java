package br.com.byiorio.desafio.jjson.repository;

import java.io.IOException;

import br.com.byiorio.desafio.jjson.entity.IJsonJapEntity;
import br.com.byiorio.desafio.jjson.exceptions.JsonJpaException;
import br.com.byiorio.desafio.jjson.utils.Arquivos;
import br.com.byiorio.desafio.jjson.utils.Diretorio;

public abstract class BaseJsonJpaRepository implements IAcoesBasicas, IJsonJpaRepository<IJsonJapEntity> {
    public BaseJsonJpaRepository() {
        this.configurar();
    }

    @Override
    public void configurar() {
        try {
            if (!Diretorio.verifica(getNome())) {
                Diretorio.criar(getNome());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IJsonJapEntity salvar(IJsonJapEntity entidade) {
        boolean novoArquivo = false;

        // Se o id estiver nulo gera o nome do arquivo
        if (entidade.getId() == null) {
            entidade.setId(entidade.gerarId());
            novoArquivo = true;
        }

        // gera caminhho
        String caminhoArquivo = getNome().concat("/").concat(entidade.getId()).concat(".json");

        if (!Arquivos.verifica(caminhoArquivo) && novoArquivo) {
            // Se for arquivo novo salva
            Arquivos.salvar(caminhoArquivo, entidade);

        } else if (Arquivos.verifica(caminhoArquivo) && !novoArquivo) {
            // Se for arquivo antigo, verifica se existe
            Arquivos.salvar(caminhoArquivo, entidade);
        } else {
            throw new JsonJpaException("Erro ao salvar arquivo");
        }

        return entidade;
    }

    public IJsonJapEntity salvar(String id, IJsonJapEntity entidade) {
        entidade.setId(id);
        return salvar(entidade);
    }

    @Override
    public IJsonJapEntity buscar(String id, Class clazz) {
        // gera caminhho
        String caminhoArquivo = getNome().concat("/").concat(id).concat(".json");

        // Salva arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            return (IJsonJapEntity) Arquivos.ler(caminhoArquivo, clazz);
        } else {
            throw new JsonJpaException(getNome() + " não encontrado");
        }

    }

}