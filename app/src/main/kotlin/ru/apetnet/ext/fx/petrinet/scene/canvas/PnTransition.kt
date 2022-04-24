package ru.apetnet.ext.fx.petrinet.scene.canvas

import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import ru.apetnet.ext.fx.java.geometry.Size2D
import ru.apetnet.ext.fx.petrinet.scene.canvas.parent.PtObjectParent
import java.util.*

data class PnTransition(
    override val id: UUID,
    override val point: Point2D,
    override val size: Size2D,
    override val name: String,
    override val description: String,
    private var tokensNumber: Int = 0
) : PtObjectParent(
    id = id,
    point = point,
    size = size,
    name = name,
    description = description
) {
    override fun render(gc: GraphicsContext) = with(gc) {
        fill = OBJECT_COLOR
        fillRect(
            center.x,
            center.y,
            size.width,
            size.height
        )

        val offsetX = (size.width / 2) * 1.2

        fill = TEXT_COLOR

        fillDescription(
            gc = gc,
            offsetX = offsetX
        )

        fillName(
            gc = gc,
            offsetX = offsetX
        )
    }

    fun addToken() {
        tokensNumber++
    }

    fun removeToken() {
        tokensNumber--
    }

    override fun toString(): String {
        return "PnTransition: $point"
    }

    companion object {
        val OBJECT_COLOR: Color = Color.GRAY
    }
}

