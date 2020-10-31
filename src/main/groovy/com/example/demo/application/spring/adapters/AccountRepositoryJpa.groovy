package com.example.demo.application.spring.adapters

import com.example.demo.application.spring.repository.AccountJpa
import com.example.demo.application.spring.repository.IAccountRepositoryJpa
import com.example.demo.business.entities.Account
import com.example.demo.business.ports.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountRepositoryJpa implements AccountRepository {

    final IAccountRepositoryJpa accountRepositoryJpa

    AccountRepositoryJpa(IAccountRepositoryJpa accountRepositoryJpa) {
        this.accountRepositoryJpa = accountRepositoryJpa
    }

    @Override
    Optional<Account> findByDocumentNumber(String documentNumber) {
        final Optional<AccountJpa> accountJpa = accountRepositoryJpa.findByDocumentNumber(documentNumber)

        return accountJpa.map({a -> new Account(a.documentNumber)})
    }

    Account save(Account account) {
        final AccountJpa saved =  accountRepositoryJpa.save(new AccountJpa(documentNumber: account.documentNumber))

        return new Account(saved.documentNumber)
    }

    @Override
    boolean existsAccountWithDocumentNumber(String documentNumber) {
        return accountRepositoryJpa.existsByDocumentNumber(documentNumber)
    }
}
