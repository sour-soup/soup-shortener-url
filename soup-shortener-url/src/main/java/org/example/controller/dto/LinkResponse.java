package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность ссылки пользователя")
public record LinkResponse(
        @JsonProperty @Schema(
                description = "Длинная ссылка",
                example = "https://example.com") String longLink,
        @JsonProperty @Schema(
                description = "Короткая ссылка",
                example = "http://my-site.com/aa1-t") String shortLink,
        @JsonProperty @Schema(
                description = "Количество посещений ссылки",
                example = "123") Long visitCount) {
}
