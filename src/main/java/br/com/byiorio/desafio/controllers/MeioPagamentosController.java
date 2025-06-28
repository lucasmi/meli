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

import br.com.byiorio.desafio.models.MeioPagamentoEntity;
import br.com.byiorio.desafio.services.MeioPagamentosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/meio-pagamentos")
public class MeioPagamentosController {

    MeioPagamentosService meioPagamentosService;

    public MeioPagamentosController(MeioPagamentosService meioPagamentosService) {
        this.meioPagamentosService = meioPagamentosService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeioPagamentoEntity> consultar(@PathVariable String id) {
        return ResponseEntity.ok(meioPagamentosService.buscar(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<MeioPagamentoEntity>> consultar() {
        return ResponseEntity.ok(meioPagamentosService.buscarTodos());
    }

    @PutMapping("{id}")
    public ResponseEntity<MeioPagamentoEntity> atualizar(@PathVariable String id,
            @Valid @RequestBody MeioPagamentoEntity entidade) {
        return ResponseEntity.ok(meioPagamentosService.atualizar(id, entidade));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(@PathVariable String id) {
        meioPagamentosService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
