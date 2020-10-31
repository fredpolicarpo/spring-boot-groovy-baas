package com.example.demo.ui

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
@JsonInclude(JsonInclude.Include.NON_NULL)
class CreateAccountResponse {
    @JsonProperty("document_number")
    String documentNumber

    @JsonProperty("created_at")
    String createdAt

    String error
}
