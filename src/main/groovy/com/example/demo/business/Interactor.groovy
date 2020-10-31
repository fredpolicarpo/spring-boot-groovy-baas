package com.example.demo.business

import com.example.demo.business.entities.Account
import com.example.demo.business.ports.AccountRepository
import com.example.demo.business.exceptions.DuplicatedAccountNumberException
import static com.example.demo.ui.CreateAccountRequestValidator.validate
import com.example.demo.ui.CreateAccountRequest
import com.example.demo.ui.CreateAccountResponse
import com.example.demo.business.ports.Timer
import groovy.transform.Canonical


@Canonical
class Interactor {
    final AccountRepository accountRepository
    final Timer timer

    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        validate(createAccountRequest)

        if(accountRepository.existsAccountWithDocumentNumber(createAccountRequest.documentNumber)) {
            throw new DuplicatedAccountNumberException(createAccountRequest.documentNumber)
        }

        final Account account = new Account(createAccountRequest.documentNumber)

        final Account savedAccount = accountRepository.save(account)

        return new CreateAccountResponse(savedAccount.documentNumber, timer.now().toString())
    }
}
