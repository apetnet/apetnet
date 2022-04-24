package ru.apetnet.ext.fx.petrinet.scene.canvas.parent

import javafx.geometry.Point2D
import javafx.geometry.VPos
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import ru.apetnet.ext.fx.java.geometry.Size2D
import java.util.*

abstract class PtObjectParent(
    override val id: UUID,
    override val point: Point2D,
    override val size: Size2D,
    override val name: String,
    override val description: String
) : PnObjectParent(
    id = id,
    point = point,
    size = size
) {
    fun fillName(gc: GraphicsContext, offsetX: Double) {
        gc.fillText(
            name,
            x + offsetX,
            y - size.height / 2
        )
    }

    fun fillDescription(gc: GraphicsContext, offsetX: Double) {
        val (prevAlign, prevBaseline) = gc.textAlign to gc.textBaseline

        gc.textAlign = TextAlignment.RIGHT
        gc.textBaseline = VPos.TOP

        gc.fillText(
            description,
            x - offsetX,
            y + size.height / 2
        )

        gc.textAlign = prevAlign
        gc.textBaseline = prevBaseline
    }

    companion object {
        val TEXT_COLOR: Color = Color.BLUE
    }
}