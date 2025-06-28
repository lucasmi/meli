package br.com.byiorio.desafio.services;

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
        return (UsuarioEntity) usuarioRepository.salvar(usuario);
    }

    public UsuarioEntity buscar(String id) {
        return (UsuarioEntity) usuarioRepository.buscar(id, UsuarioEntity.class);
    }

    public UsuarioEntity atualizar(String id, UsuarioEntity entidade) {
        return (UsuarioEntity) usuarioRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        UsuarioEntity usuarioEncontrado = (UsuarioEntity) usuarioRepository.buscar(id,
                UsuarioEntity.class);

        // Apaga todos os produtos relacionados
        usuarioEncontrado.getIdsProdutos().forEach(idProduto -> produtoRepository.apagar(idProduto));

        // Apagando usuário
        usuarioRepository.apagar(id);
    }
}
