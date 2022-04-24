package ru.apetnet.ext.fx.petrinet.scene.canvas

import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import ru.apetnet.ext.fx.petrinet.scene.canvas.parent.PnArcParent
import java.util.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class PnArc(
    override val id: UUID,
    override val points: List<Point2D>
) : PnArcParent(
    id = id,
    points = points
) {
    override fun render(gc: GraphicsContext) = with(gc) {
        fill = Color.BLACK
        lineWidth = LINE_WIDTH

        var len = 0.0

        repeat(points.size - 1) {
            val startPoint = points[it]
            val endPoint = points[it + 1]

            len += startPoint.distance(endPoint)

            strokeLine(
                startPoint.x,
                startPoint.y,
                endPoint.x,
                endPoint.y
            )
        }

        if (len > ARROW_HEAD_SIZE * 0.7 && points.size >= 2) {
            val lastIndex = points.lastIndex
            val startPoint = points[lastIndex - 1]
            val endPoint = points[lastIndex]

            val startX = startPoint.x
            val startY = startPoint.y
            val endX = endPoint.x
            val endY = endPoint.y

            // https://gist.github.com/kn0412/2086581e98a32c8dfa1f69772f14bca4
            // ArrowHead
            val angle = atan2(
                endY - startY,
                endX - startX
            ) - Math.PI / 2.0

            val sin = sin(angle)
            val cos = cos(angle)

            //point1
            val x1 = (-1.0 / 2.0 * cos + sqrt(3.0) / 2 * sin) * ARROW_HEAD_SIZE + endX
            val y1 = (-1.0 / 2.0 * sin - sqrt(3.0) / 2 * cos) * ARROW_HEAD_SIZE + endY

            //point2
            val x2 = (1.0 / 2.0 * cos + sqrt(3.0) / 2 * sin) * ARROW_HEAD_SIZE + endX
            val y2 = (1.0 / 2.0 * sin - sqrt(3.0) / 2 * cos) * ARROW_HEAD_SIZE + endY

            gc.fillPolygon(
                doubleArrayOf(
                    x1,
                    x2,
                    endX
                ),
                doubleArrayOf(
                    y1,
                    y2,
                    endY
                ),
                3
            )
        }
    }

    override fun toString(): String {
        return "PnArc: [$points]"
    }

    companion object {
        const val ARROW_HEAD_SIZE = 12.5
    }
}
