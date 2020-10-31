package com.fredpolicarpo.baas.mocks

import com.fredpolicarpo.baas.business.ports.Timer
import groovy.transform.Canonical

import java.time.Instant

@Canonical
class TimerMock implements Timer {
    final Instant instant

    @Override
    Instant now() {
        return instant
    }
}
