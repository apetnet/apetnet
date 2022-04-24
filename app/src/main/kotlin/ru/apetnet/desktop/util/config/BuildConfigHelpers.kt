package ru.apetnet.desktop.util.config

import javafx.geometry.Orientation
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.ext.fx.java.geometry.Size2D
import ru.apetnet.ext.fx.java.geometry.size2D

fun getTransitionSize(orientation: Orientation): Size2D {
    return when (orientation) {
        Orientation.HORIZONTAL -> size2D(
            width = BuildConfig.TRANSITION_SMALLER_SIZE, 
            height = BuildConfig.TRANSITION_LARGER_SIZE
        )
        Orientation.VERTICAL -> size2D(
            width = BuildConfig.TRANSITION_LARGER_SIZE,
            height = BuildConfig.TRANSITION_SMALLER_SIZE
        )
    }
}
