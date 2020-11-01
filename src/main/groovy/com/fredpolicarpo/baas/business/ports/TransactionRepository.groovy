package com.fredpolicarpo.baas.business.ports

import com.fredpolicarpo.baas.business.entities.Transaction

interface TransactionRepository {
    Optional<Transaction> findById(final Long id)

    Transaction save(final Transaction account)
}
