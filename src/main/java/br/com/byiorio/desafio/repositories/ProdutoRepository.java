package br.com.byiorio.desafio.repositories;

import org.springframework.stereotype.Component;

import br.com.byiorio.desafio.jjson.repository.BaseJsonJpaRepository;

@Component
public class ProdutoRepository extends BaseJsonJpaRepository {

    @Override
    public String getNome() {
        return "db/produtos";
    }

}
