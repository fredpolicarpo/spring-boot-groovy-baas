package com.fredpolicarpo.baas.ui.api

import com.fredpolicarpo.baas.ui.GetAccountResponse

interface GetAccountPresenter {
    void showAccount(final GetAccountResponse response)

    void showError(final Exception exception)
}