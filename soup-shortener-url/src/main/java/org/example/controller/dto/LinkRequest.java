package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkRequest(@JsonProperty String link) {
}
