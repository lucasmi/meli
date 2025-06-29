package br.com.byiorio.desafio.repositories;

import org.springframework.stereotype.Component;

import br.com.byiorio.desafio.jjson.config.JpajsonConfig;
import br.com.byiorio.desafio.jjson.repository.CrudJpaJsonRepository;

@Component
public class MeioPagamentoRepository extends CrudJpaJsonRepository {

    public static final String NOME_PASTA = "meiopagamentos";

    protected MeioPagamentoRepository(JpajsonConfig jpajsonConfig) {
        super(jpajsonConfig);
    }

    @Override
    public String getNome() {
        return NOME_PASTA;
    }

}
