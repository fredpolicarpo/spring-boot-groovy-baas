package com.example.demo

import com.example.demo.business.ports.Timer
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
