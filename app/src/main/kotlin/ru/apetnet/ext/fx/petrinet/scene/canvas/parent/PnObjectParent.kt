package ru.apetnet.ext.fx.petrinet.scene.canvas.parent

import javafx.geometry.Point2D
import javafx.geometry.Rectangle2D
import ru.apetnet.ext.fx.java.geometry.Size2D
import ru.apetnet.ext.fx.java.geometry.point2D
import ru.apetnet.ext.fx.java.geometry.rectangle2D
import java.util.*

abstract class PnObjectParent(
    open val id: UUID,
    override val point: Point2D,
    override val size: Size2D
) : PnObject {
    override val x: Double
        get() = point.x
    override val y: Double
        get() = point.y
    override val width: Double
        get() = size.width
    override val height: Double
        get() = size.height

    override val center: Point2D
        get() = point2D(
            x = point.x - size.width / 2,
            y = point.y - size.height / 2
        )

    override val boundary: Rectangle2D
        get() = rectangle2D(
            point = point,
            size = size
        )
}