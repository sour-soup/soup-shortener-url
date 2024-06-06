package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность с длинной ссылкой")
public record LinkRequest(
        @JsonProperty @Schema(description = "Cсылка", example = "https://example.com") String link) {
}
