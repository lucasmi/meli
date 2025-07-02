package br.com.byiorio.desafio.jjson.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.byiorio.desafio.jjson.entity.IJapJsonEntity;
import br.com.byiorio.desafio.jjson.repository.IJpaJsonRepository;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ManyToMany {
    Class<? extends IJpaJsonRepository<IJapJsonEntity>> repositoryTarget();

    Class<? extends IJapJsonEntity> entityTarget();

    String mappedBy();

    Class<? extends IJpaJsonRepository<IJapJsonEntity>> repositorySource();

    Class<? extends IJapJsonEntity> entitySource();
}