package br.com.byiorio.desafio.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.byiorio.desafio.models.BasicErrorDTO;
import br.com.byiorio.desafio.models.MeioPagamentoEntity;
import br.com.byiorio.desafio.services.MeioPagamentosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/meio-pagamentos")
@Tag(name = "Meios de Pagamento", description = "Operações relacionadas aos meios de pagamento dos usuários")
public class MeioPagamentosController {
    MeioPagamentosService meioPagamentosService;

    public MeioPagamentosController(MeioPagamentosService meioPagamentosService) {
        this.meioPagamentosService = meioPagamentosService;
    }

    @Operation(summary = "Buscar meio de pagamento por ID", description = "Retorna os dados de um meio de pagamento pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Meio de pagamento encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Meio de pagamento não encontrado", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<MeioPagamentoEntity> consultar(
            @Parameter(description = "ID do meio de pagamento", example = "abc123") @PathVariable String id) {
        return ResponseEntity.ok(meioPagamentosService.buscar(id));
    }

    @Operation(summary = "Listar todos os meios de pagamento", description = "Retorna uma lista de todos os meios de pagamento cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de meios de pagamento retornada com sucesso")
    @GetMapping("/")
    public ResponseEntity<List<MeioPagamentoEntity>> consultar() {
        return ResponseEntity.ok(meioPagamentosService.buscarTodos());
    }

    @Operation(summary = "Apagar meio de pagamento", description = "Remove um meio de pagamento do sistema.")
    @ApiResponse(responseCode = "204", description = "Meio de pagamento removido com sucesso")
    @ApiResponse(responseCode = "404", description = "Meio de pagamento não encontrado", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(
            @Parameter(description = "ID do meio de pagamento", example = "abc123") @PathVariable String id) {
        meioPagamentosService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
