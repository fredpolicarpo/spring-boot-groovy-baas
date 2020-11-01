package com.fredpolicarpo.baas.ui.api


import com.fredpolicarpo.baas.ui.CreateTransactionResponse
import groovy.transform.Canonical

@Canonical
class CreateTransactionResponseApi {
    CreateTransactionResponse response
    int httpStatus
}
