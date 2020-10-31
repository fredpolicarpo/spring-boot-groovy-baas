package com.example.demo.ui

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class CreateAccountRequest {
    @JsonProperty("document_number")
    String documentNumber
}
