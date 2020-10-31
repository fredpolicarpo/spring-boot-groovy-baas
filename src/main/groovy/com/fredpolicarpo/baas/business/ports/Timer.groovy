package com.fredpolicarpo.baas.business.ports

import java.time.Instant

interface Timer {
    Instant now()
}