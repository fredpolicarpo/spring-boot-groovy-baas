package com.example.demo.business.ports

import java.time.Instant

interface Timer {
    Instant now()
}