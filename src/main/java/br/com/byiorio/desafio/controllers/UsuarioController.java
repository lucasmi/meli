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

import br.com.byiorio.desafio.models.BasicErrorDTO;
import br.com.byiorio.desafio.models.MeioPagamentoDTO;
import br.com.byiorio.desafio.models.MeioPagamentoEntity;
import br.com.byiorio.desafio.models.UsuarioEntity;
import br.com.byiorio.desafio.services.MeioPagamentosService;
import br.com.byiorio.desafio.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public class UsuarioController {

    UsuarioService usuarioService;
    MeioPagamentosService meioPagamentosService;

    public UsuarioController(UsuarioService usuarioService, MeioPagamentosService meioPagamentosService) {
        this.usuarioService = usuarioService;
        this.meioPagamentosService = meioPagamentosService;
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de parametrização", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> consultar(
            @Parameter(description = "ID do usuário", example = "1eb2bc27-7ae6-472f-9422-cd53fbce22f9") @PathVariable String id) {
        return ResponseEntity.ok(usuarioService.buscar(id));
    }

    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de parametrização", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @GetMapping("/")
    public ResponseEntity<List<UsuarioEntity>> consultar() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @Operation(summary = "Cadastrar um novo usuário", description = "Cadastra um novo usuário no sistema.")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de parametrização", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioEntity> criar(@Valid @RequestBody UsuarioEntity entidade) {
        UsuarioEntity usuarioCriado = usuarioService.criar(entidade);
        URI uri = URI.create("/usuarios/".concat(usuarioCriado.gerarId()));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(usuarioCriado);
    }

    @Operation(summary = "Atualizar dados do usuário", description = "Atualiza os dados de um usuário existente.")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de parametrização", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @PutMapping("{id}")
    public ResponseEntity<UsuarioEntity> atualizar(
            @Parameter(description = "ID do usuário", example = "1eb2bc27-7ae6-472f-9422-cd53fbce22f9") @PathVariable String id,
            @Valid @RequestBody UsuarioEntity entidade) {
        return ResponseEntity.ok(usuarioService.atualizar(id, entidade));
    }

    @Operation(summary = "Apagar usuário", description = "Remove um usuário do sistema.")
    @ApiResponse(responseCode = "204", description = "Operação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de parametrização", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @DeleteMapping("{id}")
    public ResponseEntity<Void> apagar(
            @Parameter(description = "ID do usuário", example = "1eb2bc27-7ae6-472f-9422-cd53fbce22f9") @PathVariable String id) {
        usuarioService.apagar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Insere modo de pagamento", description = "Insere um meio de pagamento para o usuário.")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de parametrização", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @PostMapping(path = "/{id}/meios-pagamentos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeioPagamentoEntity> meioPagamentos(
            @Parameter(description = "ID do usuário", example = "1eb2bc27-7ae6-472f-9422-cd53fbce22f9") @PathVariable String id,
            @Valid @RequestBody MeioPagamentoDTO request) {

        MeioPagamentoEntity meioPagamentoEntity = meioPagamentosService
                .criar(MeioPagamentoEntity.builder()
                        .cartaoCredito(request.getCartaoCredito())
                        .idUsuario(id).build());

        URI uri = URI.create("/meios-pagamento/".concat(meioPagamentoEntity.gerarId()));
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(meioPagamentoEntity);
    }

    @Operation(summary = "Atualiza meio de pagamento", description = "Atualiza um meio de pagamento existente para o usuário.")
    @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de parametrização", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = BasicErrorDTO.class)))
    @PutMapping("{id}/meios-pagamentos/{meioPagamentoId}")
    public ResponseEntity<MeioPagamentoEntity> atualizar(
            @Parameter(description = "ID do usuário", example = "1eb2bc27-7ae6-472f-9422-cd53fbce22f9") @PathVariable String id,
            @Parameter(description = "ID do pagamento", example = "1eb2bc27-7ae6-472f-9422-cd53fbce22f9") @PathVariable String meioPagamentoId,
            @Valid @RequestBody MeioPagamentoDTO request) {

        MeioPagamentoEntity meioPagamentoEntity = meioPagamentosService.atualizar(meioPagamentoId,
                MeioPagamentoEntity.builder()
                        .cartaoCredito(request.getCartaoCredito())
                        .idUsuario(id).build());

        return ResponseEntity.ok(meioPagamentoEntity);
    }

}
