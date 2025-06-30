package br.com.byiorio.desafio.services;

import java.util.List;

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
        return usuarioRepository.salvar(usuario);
    }

    public UsuarioEntity buscar(String id) {
        return usuarioRepository.buscar(id, UsuarioEntity.class);
    }

    public List<UsuarioEntity> buscarTodos() {
        return usuarioRepository.buscarTodos(UsuarioEntity.class);
    }

    public UsuarioEntity atualizar(String id, @Valid UsuarioEntity entidade) {
        return usuarioRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        usuarioRepository.apagar(id, UsuarioEntity.class);
    }
}
