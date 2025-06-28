package br.com.byiorio.desafio.services;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity criar(@Valid UsuarioEntity usuario) {
        return (UsuarioEntity) usuarioRepository.salvar(usuario);
    }

    public UsuarioEntity buscar(String id) {
        return (UsuarioEntity) usuarioRepository.buscar(id, UsuarioEntity.class);
    }

    public UsuarioEntity atualizar(String id, UsuarioEntity entidade) {
        return (UsuarioEntity) usuarioRepository.salvar(id, entidade);
    }
}
