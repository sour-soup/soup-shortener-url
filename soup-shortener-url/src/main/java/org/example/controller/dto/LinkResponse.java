package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkResponse(@JsonProperty String longLink,
                           @JsonProperty String shortLink,
                           @JsonProperty Long visitCount) {
}
