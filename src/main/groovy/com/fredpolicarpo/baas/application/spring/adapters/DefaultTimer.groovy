package com.fredpolicarpo.baas.application.spring.adapters


import com.fredpolicarpo.baas.business.ports.Timer
import org.springframework.stereotype.Service

import java.time.Instant

@Service
class DefaultTimer implements Timer {
    @Override
    Instant now() {
        return Instant.now()
    }
}
