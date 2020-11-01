package com.fredpolicarpo.baas.ui.api.ports


import com.fredpolicarpo.baas.ui.CreateTransactionResponse
import com.fredpolicarpo.baas.ui.api.CreateTransactionResponseApi

interface CreateTransactionPresenter {
    CreateTransactionResponseApi buildApiResponse(final CreateTransactionResponse response)

    CreateTransactionResponseApi buildApiResponse(final Exception exception)
}