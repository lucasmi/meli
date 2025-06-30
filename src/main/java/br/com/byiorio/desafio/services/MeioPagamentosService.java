package br.com.byiorio.desafio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.byiorio.desafio.exceptions.NegocialException;
import br.com.byiorio.desafio.models.MeioPagamentoEntity;
import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.repositories.MeioPagamentoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class MeioPagamentosService {
    private UsuarioRepository usuarioRepository;

    private MeioPagamentoRepository meioPagamentoRepository;

    public MeioPagamentosService(UsuarioRepository usuarioRepository, MeioPagamentoRepository meioPagamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.meioPagamentoRepository = meioPagamentoRepository;
    }

    public MeioPagamentoEntity criar(@Valid MeioPagamentoEntity entidade) {
        // Verifica se existe usuario
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(entidade.getIdUsuario(),
                UsuarioEntity.class);

        // Salva meioPagamento
        MeioPagamentoEntity meioPagamento = meioPagamentoRepository.salvar(entidade);

        // Salva meioPagamento no usuario, para registrar que ele fez essa meioPagamento
        usuarioEncontrado.getIdsMeioPagamentos().add(meioPagamento.getId());
        usuarioRepository.salvar(usuarioEncontrado);

        return meioPagamento;
    }

    public MeioPagamentoEntity buscar(String id) {
        return meioPagamentoRepository.buscar(id, MeioPagamentoEntity.class);
    }

    public List<MeioPagamentoEntity> buscarTodos() {
        return meioPagamentoRepository.buscarTodos(MeioPagamentoEntity.class);
    }

    public MeioPagamentoEntity atualizar(String id, MeioPagamentoEntity entidade) {
        // Verifica se existe Meio de pagamento
        MeioPagamentoEntity meioPagamento = meioPagamentoRepository.buscar(id,
                MeioPagamentoEntity.class);

        // Verifica se a relacao de usuario esta correta
        if (!meioPagamento.getIdUsuario().equals(entidade.getIdUsuario())) {
            throw new NegocialException("Usuario nao pode ser alterado");
        }

        // Verifica se existe usuario
        usuarioRepository.buscar(entidade.getIdUsuario(), UsuarioEntity.class);

        // Salva e retorna valores
        return meioPagamentoRepository.salvar(id, entidade);
    }

    public void apagar(String id) {
        // Encontra meioPagamento
        MeioPagamentoEntity meiosPagamentosEncontrados = meioPagamentoRepository.buscar(id,
                MeioPagamentoEntity.class);

        // Verifica se existe usuario
        UsuarioEntity usuarioEncontrado = usuarioRepository.buscar(
                meiosPagamentosEncontrados.getIdUsuario(),
                UsuarioEntity.class);

        // Apaga todos os registros relacionados
        usuarioEncontrado.getIdsMeioPagamentos().remove(id);
        usuarioRepository.salvar(usuarioEncontrado);

        // Apaga meio de pagamentos
        meioPagamentoRepository.apagar(id);
    }
}
