package com.fredpolicarpo.baas.business

import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.business.exceptions.AccountNotFoundException
import com.fredpolicarpo.baas.business.ports.AccountRepository
import com.fredpolicarpo.baas.business.exceptions.DuplicatedAccountNumberException
import com.fredpolicarpo.baas.ui.CreateAccountRequest
import com.fredpolicarpo.baas.ui.CreateAccountResponse
import com.fredpolicarpo.baas.ui.GetAccountResponse

import static com.fredpolicarpo.baas.ui.CreateAccountRequestValidator.validate

import com.fredpolicarpo.baas.business.ports.Timer
import groovy.transform.Canonical


@Canonical
class Interactor {
    final AccountRepository accountRepository
    final Timer timer

    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        validate(createAccountRequest)

        if (accountRepository.existsAccountWithDocumentNumber(createAccountRequest.documentNumber)) {
            throw new DuplicatedAccountNumberException(createAccountRequest.documentNumber)
        }

        final Account account = new Account(documentNumber: createAccountRequest.documentNumber)

        final Account savedAccount = accountRepository.save(account)

        return new CreateAccountResponse(savedAccount.documentNumber, String.valueOf(savedAccount.id), timer.now().toString())
    }

    GetAccountResponse getAccount(final Long id) {
        final Optional<Account> optionalAccount = accountRepository.findById(id)

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException(id)
        }

        return new GetAccountResponse(documentNumber: optionalAccount.get().documentNumber, accountId: optionalAccount.get().id)
    }
}
