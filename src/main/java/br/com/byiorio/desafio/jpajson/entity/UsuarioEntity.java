package br.com.byiorio.desafio.jpajson.entity;

import java.io.IOException;

import org.springframework.stereotype.Component;

import br.com.byiorio.desafio.jpajson.repository.IJsonJpaRepository;
import br.com.byiorio.desafio.utils.Diretorio;
import br.com.byiorio.desafio.utils.StringUtils;

@Component
public class UsuarioEntity<UsuarioDTO> implements IJsonJapEntity {

    @Override
    public String getIdentificador() {
        return StringUtils.limpa("");
    }

    @Override
    public UsuarioDTO criarEntidade() {
        return UsuarioDTO.bv
    }

}
