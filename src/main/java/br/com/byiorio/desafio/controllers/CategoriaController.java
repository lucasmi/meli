package br.com.byiorio.desafio.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.BasicErrorDTO;
import br.com.byiorio.desafio.models.CategoriaEntity;
import br.com.byiorio.desafio.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/categorias")
@Tag(name = "Categorias", description = "Cria categorias  dos produtos")
public class CategoriaController {
    CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Buscar categoria por ID", description = "Retorna os dados de uma categoria pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Categoria encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrado", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEntity> consultar(
            @Parameter(description = "ID da categoria", example = "abc123") @PathVariable String id) {
        return ResponseEntity.ok(categoriaService.buscar(id));
    }

    @Operation(summary = "Listar todos os categorias", description = "Retorna uma lista de todos os categorias cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    @GetMapping("/")
    public ResponseEntity<List<CategoriaEntity>> consultar() {
        return ResponseEntity.ok(categoriaService.buscarTodos());
    }

    @Operation(summary = "Apagar categoria", description = "Remove uma categoria do sistema.")
    @ApiResponse(responseCode = "204", description = "Categoria removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrado", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(
            @Parameter(description = "ID da categoria", example = "abc123") @PathVariable String id) {
        categoriaService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
