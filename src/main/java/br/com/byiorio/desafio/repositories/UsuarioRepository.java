package br.com.byiorio.desafio.repositories;

import org.springframework.stereotype.Component;

import br.com.byiorio.desafio.jjson.repository.CrudJsonJpaRepository;

@Component
public class UsuarioRepository extends CrudJsonJpaRepository {

    @Override
    public String getNome() {
        return "db/usuarios";
    }

}
