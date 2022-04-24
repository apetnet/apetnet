package ru.apetnet.ext.fx.java.geometry

import javafx.geometry.Point2D
import javafx.geometry.Rectangle2D

fun rectangle2D(
    x: Double,
    y: Double,
    width: Double,
    height: Double
): Rectangle2D {
    return Rectangle2D(
        x,
        y,
        width,
        height
    )
}

fun rectangle2D(
    x: Int,
    y: Int,
    width: Int,
    height: Int
): Rectangle2D {
    return rectangle2D(
        x = x.toDouble(),
        y = y.toDouble(),
        width = width.toDouble(),
        height = height.toDouble()
    )
}

fun rectangle2D(
    point: Point2D,
    size: Size2D
): Rectangle2D {
    return Rectangle2D(
        point.x,
        point.y,
        size.width,
        size.height
    )
}

fun point2D(x: Double, y: Double): Point2D {
    return Point2D(
        x,
        y
    )
}

fun point2D(x: Int, y: Int): Point2D {
    return Point2D(
        x.toDouble(),
        y.toDouble()
    )
}