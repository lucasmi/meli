package br.com.byiorio.desafio.repositories;

import org.springframework.stereotype.Component;

import br.com.byiorio.desafio.jjson.config.JpajsonConfig;
import br.com.byiorio.desafio.jjson.repository.CrudJpaJsonRepository;

@Component
public class MeioPagamentoRepository extends CrudJpaJsonRepository {

    protected MeioPagamentoRepository(JpajsonConfig jpajsonConfig) {
        super(jpajsonConfig);
    }

    @Override
    public String getNome() {
        return "meiopagamentos";
    }

}
