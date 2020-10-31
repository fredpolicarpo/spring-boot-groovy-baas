package com.fredpolicarpo.baas.business.ports


import com.fredpolicarpo.baas.business.entities.Account

interface AccountRepository {
    Optional<Account> findByDocumentNumber(final String documentNumber)

    Account save(final Account account)

    boolean existsAccountWithDocumentNumber(final String documentNumber)
}
