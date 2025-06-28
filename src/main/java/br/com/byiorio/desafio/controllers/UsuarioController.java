package br.com.byiorio.desafio.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public ResponseEntity<UsuarioEntity> consultar(@RequestParam String id) {
        return ResponseEntity.ok(usuarioService.buscar(id));
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioEntity> item(@Valid @RequestBody UsuarioEntity entidade) {
        return ResponseEntity.ok(usuarioService.criar(entidade));
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioEntity> atualizar(@PathVariable String id, @RequestBody UsuarioEntity entidade) {
        return ResponseEntity.ok(usuarioService.atualizar(id, entidade));
    }
}
