package ru.apetnet.ext.fx.java.geometry

data class Size2D(
    val width: Double,
    val height: Double
) {
    companion object {
        val ZERO: Size2D = Size2D(0.0, 0.0)
    }
}

fun size2D(width: Int, height: Int): Size2D {
    return Size2D(
        width = width.toDouble(),
        height = height.toDouble()
    )
}

fun size2D(width: Double, height: Double): Size2D {
    return Size2D(
        width = width,
        height = height
    )
}

fun size2D(size: Double): Size2D {
    return Size2D(
        width = size,
        height = size
    )
}