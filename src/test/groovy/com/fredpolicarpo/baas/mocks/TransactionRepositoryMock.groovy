package com.fredpolicarpo.baas.mocks


import com.fredpolicarpo.baas.business.entities.Transaction
import com.fredpolicarpo.baas.business.ports.TransactionRepository

class TransactionRepositoryMock implements TransactionRepository {
    private final Map<Long, Transaction> dbById = [:]

    private Long idGenerator = 1

    @Override
    Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(dbById[id])
    }

    @Override
    Transaction save(Transaction transaction) {
        transaction.id = idGenerator++
        dbById[transaction.id] = transaction
    }

    void clean() {
        dbById.clear()
    }
}
