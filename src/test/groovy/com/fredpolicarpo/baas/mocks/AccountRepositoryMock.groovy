package com.fredpolicarpo.baas.mocks


import com.fredpolicarpo.baas.business.entities.Account
import com.fredpolicarpo.baas.business.ports.AccountRepository

class AccountRepositoryMock implements AccountRepository {

    private final Map<String, Account> dbByDocumentNumber = [:]
    private final Map<Long, Account> dbById = [:]

    private Long idGenerator = 1

    @Override
    Optional<Account> findByDocumentNumber(final String documentNumber) {
        return Optional.ofNullable(dbByDocumentNumber[documentNumber])
    }

    @Override
    Optional<Account> findById(Long id) {
        return Optional.ofNullable(dbById[id])
    }

    @Override
    Account save(Account account) {
        account.id = idGenerator++
        dbByDocumentNumber[account.documentNumber] = account
        dbById[account.id] = account
    }

    @Override
    boolean existsAccountWithDocumentNumber(String documentNumber) {
        return dbByDocumentNumber.containsKey(documentNumber)
    }

    void clean() {
        dbByDocumentNumber.clear()
        dbById.clear()
    }
}
