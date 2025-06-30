package br.com.byiorio.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private ProdutoRepository produtoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
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

    public UsuarioEntity atualizar(String id, UsuarioEntity entidade) {
        return usuarioRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        // Verifica se existe usuário
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(id,
                UsuarioEntity.class);

        // Apaga todos os produtos relacionados aos produtos
        usuarioEncontrado.getIdsProdutos().forEach(idProduto -> produtoRepository.apagar(idProduto));

        // Apagando usuário
        usuarioRepository.apagar(id);
    }
}
