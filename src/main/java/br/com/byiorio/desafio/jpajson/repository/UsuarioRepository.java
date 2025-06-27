package br.com.byiorio.desafio.jpajson.repository;

import org.springframework.stereotype.Component;

@Component
public class UsuarioRepository extends BaseJsonJpaRepository {

    @Override
    public String getNomePasta() {
        return "db/usuarios";
    }

}
