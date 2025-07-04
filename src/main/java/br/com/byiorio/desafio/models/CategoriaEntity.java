package br.com.byiorio.desafio.models;

import java.util.HashSet;

import br.com.byiorio.desafio.jjson.annotations.GeneratedValue;
import br.com.byiorio.desafio.jjson.annotations.Id;
import br.com.byiorio.desafio.jjson.annotations.OneToMany;
import br.com.byiorio.desafio.jjson.annotations.Unique;
import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.repositories.CategoriaRepository;
import br.com.byiorio.desafio.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaEntity implements IJapJsonEntity {

    @Id
    @GeneratedValue(strategy = GeneratedValue.Strategy.UUID)
    @Schema(hidden = true)
    String id;

    @NotBlank
    @Size(max = 100)
    @Unique(uniqueIndexName = "categoriaNome")
    String nome;

    @Schema(hidden = true)
    @OneToMany(deleteCascade = false, repositoryTarget = ProdutoRepository.class, entityTarget = ProdutoEntity.class, repositorySource = CategoriaRepository.class, entitySource = CategoriaEntity.class)
    HashSet<String> idsProdutos = new HashSet<>();

}
