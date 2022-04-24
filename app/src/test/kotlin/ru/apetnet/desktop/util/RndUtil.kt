package ru.apetnet.desktop.util

import kotlin.random.*

internal object RndUtil {
    fun inRange(start: Int, end: Int): Int {
        return Random.nextInt(IntRange(start, end))
    }
}