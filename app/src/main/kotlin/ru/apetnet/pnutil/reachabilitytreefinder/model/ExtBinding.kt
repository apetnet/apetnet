package ru.apetnet.pnutil.reachabilitytreefinder.model

import java.util.*

data class ExtBinding(
    val source: UUID,
    val receiver: UUID,
    val transitionIndex: Int
)