package br.com.byiorio.desafio.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.MeioPagamentoDTO;
import br.com.byiorio.desafio.models.MeioPagamentoEntity;
import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.services.MeioPagamentosService;
import br.com.byiorio.desafio.services.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    UsuarioService usuarioService;
    MeioPagamentosService meioPagamentosService;

    public UsuarioController(UsuarioService usuarioService, MeioPagamentosService meioPagamentosService) {
        this.usuarioService = usuarioService;
        this.meioPagamentosService = meioPagamentosService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> consultar(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.buscar(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioEntity>> consultar() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioEntity> item(@Valid @RequestBody UsuarioEntity entidade) {
        UsuarioEntity usuarioCriado = usuarioService.criar(entidade);
        URI uri = URI.create("/usuarios/".concat(usuarioCriado.gerarId()));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(usuarioCriado);
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioEntity> atualizar(@PathVariable String id,
            @Valid @RequestBody UsuarioEntity entidade) {
        return ResponseEntity.ok(usuarioService.atualizar(id, entidade));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable String id) {
        usuarioService.apagar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/meios-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeioPagamentoEntity> meioPagamentos(@PathVariable String id,
            @Valid @RequestBody MeioPagamentoDTO request) {

        MeioPagamentoEntity usuarioCriado = meioPagamentosService
                .criar(MeioPagamentoEntity.builder().cartaoCredito(request.getCartaoCredito())
                        .idUsuario(id).build());

        URI uri = URI.create("/meios-pagamento/".concat(usuarioCriado.gerarId()));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(usuarioCriado);
    }
}
