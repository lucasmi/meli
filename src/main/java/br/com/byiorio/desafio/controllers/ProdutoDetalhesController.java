package br.com.byiorio.desafio.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.ProdutoDetalhadoResponse;
import br.com.byiorio.desafio.services.ProdutoDetalhadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/produtos-detalhe")
@Tag(name = "Detalhe dos protudos", description = "Operações relacionadas aos detalhes dos produtos")
public class ProdutoDetalhesController {

    ProdutoDetalhadoService produtoDetalhadoService;

    public ProdutoDetalhesController(ProdutoDetalhadoService produtoDetalhadoService) {
        this.produtoDetalhadoService = produtoDetalhadoService;
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Produto detalhado encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = br.com.byiorio.desafio.models.BasicErrorDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetalhadoResponse> consultar(
            @Parameter(description = "ID do produto detalhado", example = "abc123") @PathVariable String id) {
        return ResponseEntity.ok(produtoDetalhadoService.buscar(id));
    }

    @Operation(summary = "Listar todos os produtos detalhados", description = "Retorna uma lista com todos os produtos detalhados.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos detalhados retornada com sucesso")
    @GetMapping("/")
    public ResponseEntity<List<ProdutoDetalhadoResponse>> consultarTodos(
            @RequestParam(required = false) String idCategoria) {
        return ResponseEntity.ok(produtoDetalhadoService.buscarTodos(idCategoria));
    }
}
