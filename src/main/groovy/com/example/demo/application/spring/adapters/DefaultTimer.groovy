package com.example.demo.application.spring.adapters

import com.example.demo.business.ports.Timer
import org.springframework.stereotype.Service

import java.time.Instant

@Service
class DefaultTimer implements Timer {
    @Override
    Instant now() {
        return Instant.now()
    }
}
