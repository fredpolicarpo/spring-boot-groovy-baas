package com.fredpolicarpo.baas.business.ports


import com.fredpolicarpo.baas.business.entities.Account

interface AccountRepository {
    Optional<Account> findByDocumentNumber(final String documentNumber)

    Optional<Account> findById(final Long id)

    Account save(final Account account)

    boolean existsAccountWithDocumentNumber(final String documentNumber)
}
