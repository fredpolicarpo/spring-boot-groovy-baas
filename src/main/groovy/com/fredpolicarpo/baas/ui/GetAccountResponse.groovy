package com.fredpolicarpo.baas.ui

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
@JsonInclude(JsonInclude.Include.NON_NULL)
class GetAccountResponse {
    @JsonProperty("document_number")
    String documentNumber

    @JsonProperty("account_id")
    String accountId

    @JsonProperty("credit_limit")
    String creditLimit

    String error
}
