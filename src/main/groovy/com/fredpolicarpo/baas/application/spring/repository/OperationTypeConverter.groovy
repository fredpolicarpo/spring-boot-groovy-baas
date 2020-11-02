package com.fredpolicarpo.baas.application.spring.repository

import com.fredpolicarpo.baas.business.entities.OperationType

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class OperationTypeConverter implements AttributeConverter<OperationType, Integer> {

    @Override
    Integer convertToDatabaseColumn(OperationType operationType) {
        return operationType.code
    }

    @Override
    OperationType convertToEntityAttribute(Integer code) {
        return OperationType.fromCode(code)
    }
}
