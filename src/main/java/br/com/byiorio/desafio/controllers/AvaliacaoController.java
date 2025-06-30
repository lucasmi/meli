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

import br.com.byiorio.desafio.models.AvaliacaoEntity;
import br.com.byiorio.desafio.services.AvaliacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/avaliacoes")
@Tag(name = "Avaliações", description = "Operações relacionadas às avaliações de produtos")
public class AvaliacaoController {

    AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @Operation(summary = "Buscar avaliação por ID", description = "Retorna os dados de uma avaliação pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Avaliação encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content(schema = @Schema(implementation = br.com.byiorio.desafio.models.BasicErrorDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoEntity> consultar(
            @Parameter(description = "ID da avaliação", example = "abc123") @PathVariable String id) {
        return ResponseEntity.ok(avaliacaoService.buscar(id));
    }

    @Operation(summary = "Listar todas as avaliações", description = "Retorna uma lista de todas as avaliações cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso")
    @GetMapping("/")
    public ResponseEntity<List<AvaliacaoEntity>> consultar() {
        return ResponseEntity.ok(avaliacaoService.buscarTodos());
    }

    @Operation(summary = "Cadastrar uma nova avaliação", description = "Cadastra uma nova avaliação para um produto.")
    @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = br.com.byiorio.desafio.models.BasicErrorDTO.class)))
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AvaliacaoEntity> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da nova avaliação") @Valid @RequestBody AvaliacaoEntity entidade) {
        AvaliacaoEntity usuarioCriado = avaliacaoService.criar(entidade);
        URI uri = URI.create("/avaliacoes/".concat(usuarioCriado.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(usuarioCriado);
    }

    @Operation(summary = "Atualizar avaliação", description = "Atualiza os dados de uma avaliação existente.")
    @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content(schema = @Schema(implementation = br.com.byiorio.desafio.models.BasicErrorDTO.class)))
    @PutMapping("{id}")
    public ResponseEntity<AvaliacaoEntity> atualizar(
            @Parameter(description = "ID da avaliação", example = "abc123") @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Novos dados da avaliação") @Valid @RequestBody AvaliacaoEntity entidade) {
        return ResponseEntity.ok(avaliacaoService.atualizar(id, entidade));
    }

    @Operation(summary = "Apagar avaliação", description = "Remove uma avaliação do sistema.")
    @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso")
    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content(schema = @Schema(implementation = br.com.byiorio.desafio.models.BasicErrorDTO.class)))
    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(
            @Parameter(description = "ID da avaliação", example = "abc123") @PathVariable String id) {
        avaliacaoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
