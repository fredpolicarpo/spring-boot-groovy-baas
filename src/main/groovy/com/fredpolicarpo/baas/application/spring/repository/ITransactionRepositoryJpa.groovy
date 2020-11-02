package com.fredpolicarpo.baas.application.spring.repository


import org.springframework.data.jpa.repository.JpaRepository

interface ITransactionRepositoryJpa extends JpaRepository<TransactionJpa, Long> {
}
