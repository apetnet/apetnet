package ru.apetnet.ext.fx.java.scene.input

import javafx.geometry.Point2D
import javafx.scene.input.MouseEvent
import ru.apetnet.ext.fx.java.geometry.point2D

fun MouseEvent.toPoint2D(): Point2D {
    return point2D(
        x = x,
        y = y
    )
}