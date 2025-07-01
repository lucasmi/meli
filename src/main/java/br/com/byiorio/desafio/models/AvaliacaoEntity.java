package br.com.byiorio.desafio.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.byiorio.desafio.jjson.annotations.ID;
import br.com.byiorio.desafio.jjson.annotations.OneToOne;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.repositories.AvaliacaoRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import br.com.byiorio.desafio.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoEntity implements IJapJsonEntity {
    @ID
    @Schema(hidden = true)
    String id;

    @NotBlank
    @Size(min = 1, max = 50)
    @OneToOne(repository = UsuarioRepository.class, entity = UsuarioEntity.class, mappedBy = "idsAvaliacoes", blockOnUpdateOf = AvaliacaoRepository.class)
    String idUsuario;

    @NotBlank
    @Size(min = 1, max = 50)
    @OneToOne(repository = ProdutoRepository.class, entity = ProdutoEntity.class, mappedBy = "idsAvaliacoes", blockOnUpdateOf = AvaliacaoRepository.class)
    String idProduto;

    @NotNull
    @Min(1)
    @Max(5)
    Integer nota;

    @Size(max = 500)
    String comentario;

    @Schema(hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime dataReview;
}
