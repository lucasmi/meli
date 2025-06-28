package br.com.byiorio.desafio.jjson.repository;

import br.com.byiorio.desafio.jjson.entity.IJsonJapEntity;
import br.com.byiorio.desafio.jjson.exceptions.JsonJpaException;
import br.com.byiorio.desafio.jjson.utils.Arquivos;
import br.com.byiorio.desafio.jjson.utils.Diretorio;

public abstract class CrudJsonJpaRepository implements IAcoesBasicas, IJsonJpaRepository<IJsonJapEntity> {

    private static final String JSON_EXTENSAO = ".json";

    protected CrudJsonJpaRepository() {
        this.configurar();
    }

    @Override
    public void configurar() {
        // Cria diretorio onde serao armazenados os Jsons
        if (!Diretorio.verifica(getNome())) {
            Diretorio.criar(getNome());
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
        String caminhoArquivo = getNome().concat("/").concat(entidade.getId()).concat(JSON_EXTENSAO);

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
        // Salva arquivo
        entidade.setId(id);
        return salvar(entidade);
    }

    @Override
    public IJsonJapEntity buscar(String id, Class clazz) {
        // Gera caminhho
        String caminhoArquivo = getNome().concat("/").concat(id).concat(JSON_EXTENSAO);

        // Salva arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            return (IJsonJapEntity) Arquivos.ler(caminhoArquivo, clazz);
        } else {
            throw new JsonJpaException(getNome() + " não encontrado");
        }

    }

    public void apagar(String id) {
        // gera caminhho
        String caminhoArquivo = getNome().concat("/").concat(id).concat(JSON_EXTENSAO);

        // Salva arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            Arquivos.apagar(caminhoArquivo);
        }
    }

}