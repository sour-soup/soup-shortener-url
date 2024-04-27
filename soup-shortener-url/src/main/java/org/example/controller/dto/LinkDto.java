package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkDto(@JsonProperty String longLink, @JsonProperty String shortLink) {
}
