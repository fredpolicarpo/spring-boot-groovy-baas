package com.example.demo.business.ports

import com.example.demo.business.entities.Account

interface AccountRepository {
    Optional<Account> findByDocumentNumber(final String documentNumber)

    Account save(final Account account)

    boolean existsAccountWithDocumentNumber(final String documentNumber)
}
