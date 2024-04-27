package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(@JsonProperty String login) {
}
