package br.com.byiorio.desafio.jjson.entity;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity implements IJapJsonEntity {

    @Schema(hidden = true)
    String id;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String gerarId() {
        String uuid = UUID.randomUUID().toString();
        return URLEncoder.encode(uuid, StandardCharsets.UTF_8);
    }
}
