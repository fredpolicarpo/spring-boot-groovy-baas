package com.fredpolicarpo.baas.ui

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class CreateTransactionResponse {
    @JsonProperty("transaction_id")
    String transactionId

    @JsonProperty("event_date")
    String eventDate

    String error
}
