package ru.apetnet.ext.fx.petrinet.scene.canvas

import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import ru.apetnet.ext.fx.java.geometry.Size2D
import ru.apetnet.ext.fx.petrinet.scene.canvas.parent.PtObjectParent
import java.math.BigInteger
import java.util.*

data class PnPosition(
    override val id: UUID,
    override val point: Point2D,
    override val size: Size2D,
    override val name: String,
    override val description: String,
    val tokensNumber: Int
) : PtObjectParent(
    id = id,
    point = point,
    size = size,
    name = name,
    description = name
) {
    private val radius: Double get() = boundary.width / 2

    override fun render(gc: GraphicsContext) = with(gc) {
        fill = OBJECT_COLOR

        fillOval(
            center.x,
            center.y,
            width,
            height
        )

        fill = TEXT_COLOR
        val offsetX = radius * 1.2

        fillName(
            gc = gc,
            offsetX = offsetX
        )

        fillDescription(
            gc = gc,
            offsetX = offsetX
        )

        fillText(
            "$tokensNumber",
            x + offsetX,
            y
        )
    }

    override fun toString(): String {
        return "PnPosition: $point"
    }

    companion object {
        val OBJECT_COLOR: Color = Color.RED
    }
}
