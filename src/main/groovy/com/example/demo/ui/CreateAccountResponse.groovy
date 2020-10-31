package com.example.demo.ui

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class CreateAccountResponse {
    @JsonProperty("document_number")
    String documentNumber

    @JsonProperty("created_at")
    String createdAt
}
