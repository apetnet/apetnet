package ru.apetnet.desktop.domain.ui.parent

import java.time.Instant

data class ControllerErrorData(
    val timestamp: Instant,
    val throwable: Throwable
)