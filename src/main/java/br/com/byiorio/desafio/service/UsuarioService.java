package br.com.byiorio.desafio.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.exceptions.NegocialException;
import br.com.byiorio.desafio.jpajson.entity.UsuarioEntity;
import br.com.byiorio.desafio.jpajson.repository.UsuarioRepository;
import br.com.byiorio.desafio.model.UsuarioDTO;
import br.com.byiorio.desafio.utils.Arquivos;
import br.com.byiorio.desafio.utils.Diretorio;
import br.com.byiorio.desafio.utils.StringUtils;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private UsuarioEntity usuarioEntity;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioEntity usuarioEntity) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioEntity = usuarioEntity;

    }

    public UsuarioDTO criar(UsuarioDTO usuario) {

        usuarioRepository.salvar(usuario);

        // identificador do arquivo
        String idUsuario = StringUtils.limpa(usuario.getEmail());

        // Verifica se já existe usuário
        if (Arquivos.verifica(USUARIOS_DIRETORIO.concat("/").concat(idUsuario))) {

        } else {
            throw new NegocialException("Usuário já existe");
        }

        return UsuarioDTO.builder().nome("nome").email("email@email.com").build();
    }

}
