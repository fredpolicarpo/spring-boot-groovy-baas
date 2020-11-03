package com.fredpolicarpo.baas.ui

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class CreateTransactionRequest {
    @JsonProperty("account_id")
    String accountId

    @JsonProperty("operation_type_id")
    String operationTypeId

    String amount
}
