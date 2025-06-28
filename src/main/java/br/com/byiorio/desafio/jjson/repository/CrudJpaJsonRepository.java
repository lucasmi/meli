package br.com.byiorio.desafio.jjson.repository;

import br.com.byiorio.desafio.jjson.config.JpajsonConfig;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.exceptions.JpaJsonException;
import br.com.byiorio.desafio.jjson.utils.Arquivos;
import br.com.byiorio.desafio.jjson.utils.Diretorio;

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

        if (!Diretorio.verifica(caminhoCompleto)) {
            Diretorio.criar(caminhoCompleto);
        }
    }

    @Override
    public IJapJsonEntity salvar(IJapJsonEntity entidade) {
        boolean novoArquivo = false;

        // Se o id estiver nulo gera o nome do arquivo
        if (entidade.getId() == null) {
            entidade.setId(entidade.gerarId());
            novoArquivo = true;
        }

        // gera caminhho
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

    public IJapJsonEntity salvar(String id, IJapJsonEntity entidade) {
        // Salva arquivo
        entidade.setId(id);
        return salvar(entidade);
    }

    @Override
    public IJapJsonEntity buscar(String id, Class clazz) {
        // Gera caminhho
        String caminhoArquivo = jpajsonConfig.getNome().concat("/").concat(getNome()).concat("/").concat(id)
                .concat(JSON_EXTENSAO);

        // Salva arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            return (IJapJsonEntity) Arquivos.ler(caminhoArquivo, clazz);
        } else {
            throw new JpaJsonException("o id " + id + " nao foi encontrado na base " + getNome());
        }

    }

    public void apagar(String id) {
        // gera caminhho
        String caminhoArquivo = jpajsonConfig.getNome().concat("/").concat(getNome()).concat("/").concat(id)
                .concat(JSON_EXTENSAO);

        // Salva arquivo
        if (Arquivos.verifica(caminhoArquivo)) {
            Arquivos.apagar(caminhoArquivo);
        }
    }

}