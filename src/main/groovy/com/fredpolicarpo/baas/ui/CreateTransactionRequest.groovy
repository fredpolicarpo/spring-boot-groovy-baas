package com.fredpolicarpo.baas.ui

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class CreateTransactionRequest {
    @JsonProperty("document_number")
    String accountId

    @JsonProperty("operation_type_id")
    String operationTypeId

    String amount
}
