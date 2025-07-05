package br.com.byiorio.desafio.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.BasicErrorDTO;
import br.com.byiorio.desafio.models.ProdutoDetalhadoResponse;
import br.com.byiorio.desafio.services.ProdutoDetalhadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/")
@Tag(name = "Endpoint principal", description = "Operações relacionadas ao endpoint principal")
public class RaizContextoCrontoller {
    ProdutoDetalhadoService produtoDetalhadoService;

    public RaizContextoCrontoller(ProdutoDetalhadoService produtoDetalhadoService) {
        this.produtoDetalhadoService = produtoDetalhadoService;
    }

    @Operation(summary = "Busca todos os produtos", description = "Retorna uma lista de todos os produtos detalhados.")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de parametrização", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @GetMapping()
    public ResponseEntity<List<ProdutoDetalhadoResponse>> consultar(
            @RequestParam(required = false) String idCategoria) {
        return ResponseEntity.ok(produtoDetalhadoService.buscarTodos(idCategoria));
    }

}
