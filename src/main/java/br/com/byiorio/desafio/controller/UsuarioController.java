package br.com.byiorio.desafio.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.model.UsuarioDTO;
import br.com.byiorio.desafio.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String consultar(@RequestParam Integer idUsuario) {
        return new String();
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> item(@Valid @RequestBody UsuarioDTO usuario) {
        return ResponseEntity.ok(usuarioService.criar(usuario));
    }

}
