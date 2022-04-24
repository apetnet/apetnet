package ru.apetnet.ext.fx.petrinet.scene.canvas.parent

import javafx.geometry.Point2D
import ru.apetnet.ext.fx.java.scene.canvas.Renderable
import java.util.*

abstract class PnArcParent(
    open val id: UUID,
    open val points: List<Point2D>
) : Renderable {
    companion object {
        const val DIRECTOR_SIZE = 8.0
        const val LINE_WIDTH = 1.0
    }
}