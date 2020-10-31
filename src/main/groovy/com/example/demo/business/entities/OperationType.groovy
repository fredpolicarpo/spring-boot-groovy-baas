package com.example.demo.business.entities

enum OperationType {
    COMPRA_A_VISTA(1),
    COMPRA_PARCELADA(2),
    SAQUE(3),
    PAGAMENTO(4),

    int code

    OperationType(final int code) {
        this.code = code
    }
}