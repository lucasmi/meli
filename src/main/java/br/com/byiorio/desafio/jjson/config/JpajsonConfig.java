package br.com.byiorio.desafio.jjson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "jpajson.base")
public class JpajsonConfig {
    private String nome;
}
