package org.example.kafka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkDeleteKafkaMessage(@JsonProperty Long id) {
}
