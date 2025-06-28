package br.com.byiorio.desafio.jjson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jpajson.base")
public class JpajsonConfig {
    private String nome;
}
