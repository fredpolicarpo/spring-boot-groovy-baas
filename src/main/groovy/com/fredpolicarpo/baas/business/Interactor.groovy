package com.fredpolicarpo.baas.business

import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.business.ports.AccountRepository
import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException

import static com.fredpolicarpo.baas.ui.CreateAccountRequestValidator.validate

import com.fredpolicarpo.baas.business.ports.Timer
import groovy.transform.Canonical


@Canonical
class Interactor {
    final AccountRepository accountRepository
    final Timer timer

    com.fredpolicarpo.baas.ui.CreateAccountResponse createAccount(com.fredpolicarpo.baas.ui.CreateAccountRequest createAccountRequest) {
        validate(createAccountRequest)

        if(accountRepository.existsAccountWithDocumentNumber(createAccountRequest.documentNumber)) {
            throw new DuplicatedAccountNumberException(createAccountRequest.documentNumber)
        }

        final Account account = new Account(createAccountRequest.documentNumber)

        final Account savedAccount = accountRepository.save(account)

        return new com.fredpolicarpo.baas.ui.CreateAccountResponse(savedAccount.documentNumber, timer.now().toString())
    }
}
