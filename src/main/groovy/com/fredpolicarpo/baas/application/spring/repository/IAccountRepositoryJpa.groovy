package com.fredpolicarpo.baas.application.spring.repository


import org.springframework.data.jpa.repository.JpaRepository

interface IAccountRepositoryJpa extends JpaRepository<AccountJpa, Long> {
    Optional<AccountJpa> findByDocumentNumber(final String documentNumber)

    boolean existsByDocumentNumber(final String documentNumber)
}
