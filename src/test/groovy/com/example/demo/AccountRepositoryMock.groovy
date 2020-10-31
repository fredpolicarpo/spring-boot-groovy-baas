package com.example.demo

import com.example.demo.business.entities.Account
import com.example.demo.business.ports.AccountRepository

class AccountRepositoryMock implements AccountRepository {

    private final Map<String, Account> db = [:]

    @Override
    Optional<Account> findByDocumentNumber(final String documentNumber) {
        return Optional.ofNullable(db[documentNumber])
    }

    @Override
    Account save(Account account) {
        db[account.documentNumber] = account
    }

    @Override
    boolean existsAccountWithDocumentNumber(String documentNumber) {
        return db.containsKey(documentNumber)
    }
}
