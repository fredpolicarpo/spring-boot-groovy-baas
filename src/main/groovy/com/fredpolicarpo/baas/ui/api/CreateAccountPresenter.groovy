package com.fredpolicarpo.baas.ui.api

import com.fredpolicarpo.baas.ui.CreateAccountResponse

interface CreateAccountPresenter {
    void showAccount(final CreateAccountResponse response)

    void showError(final Exception exception)
}