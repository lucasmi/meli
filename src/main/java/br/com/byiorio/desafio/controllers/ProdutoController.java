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

import br.com.byiorio.desafio.models.ProdutoEntity;
import br.com.byiorio.desafio.models.BasicErrorDTO;
import br.com.byiorio.desafio.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos")
public class ProdutoController {

    ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os dados de um produto pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoEntity> consultar(
            @Parameter(description = "ID do produto", example = "abc123") @PathVariable String id) {
        return ResponseEntity.ok(produtoService.buscar(id));
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @GetMapping("/")
    public ResponseEntity<List<ProdutoEntity>> consultar() {
        return ResponseEntity.ok(produtoService.buscarTodos());
    }

    @Operation(summary = "Cadastrar um novo produto", description = "Cadastra um novo produto no sistema.")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdutoEntity> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do novo produto") @Valid @RequestBody ProdutoEntity entidade) {

        ProdutoEntity produtoCriado = produtoService.criar(entidade);
        URI uri = URI.create("/produtos/".concat(produtoCriado.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(produtoCriado);

    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente.")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @PutMapping("{id}")
    public ResponseEntity<ProdutoEntity> atualizar(
            @Parameter(description = "ID do produto", example = "abc123") @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Novos dados do produto") @RequestBody ProdutoEntity entidade) {
        return ResponseEntity.ok(produtoService.atualizar(id, entidade));
    }

    @Operation(summary = "Apagar produto", description = "Remove um produto do sistema.")
    @ApiResponse(responseCode = "204", description = "Produto removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(
            @Parameter(description = "ID do produto", example = "abc123") @PathVariable String id) {
        produtoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
