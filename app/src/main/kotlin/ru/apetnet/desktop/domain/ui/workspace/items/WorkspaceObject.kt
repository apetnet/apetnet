package ru.apetnet.desktop.domain.ui.workspace.items

import javafx.geometry.Orientation
import javafx.geometry.Point2D
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.common.HasGeometryProperties
import ru.apetnet.desktop.util.config.getTransitionSize
import ru.apetnet.ext.fx.java.geometry.Size2D
import ru.apetnet.ext.fx.java.geometry.point2D
import ru.apetnet.ext.fx.java.geometry.rectangle2D
import ru.apetnet.ext.fx.java.geometry.size2D
import ru.apetnet.ext.fx.petrinet.scene.canvas.PnArc
import ru.apetnet.ext.fx.petrinet.scene.canvas.PnPosition
import ru.apetnet.ext.fx.petrinet.scene.canvas.PnTransition
import java.util.*

sealed class WorkspaceObject(
    @Deprecated("Отказаться от index, так как есть id")
    private val index: Long = -1,
    open val id: UUID,
    open val name: String,
    open val description: String,
) {
    data class Position(
        override val id: UUID = UUID.randomUUID(),
        override val name: String,
        override val description: String,
        val tokensNumber: Int = 0,
        override var centerPoint: Point2D,
        override val size: Size2D,
        override val isMoving: Boolean = false,
        override val orientation: Orientation
    ) : WorkspaceObject(
        id = id,
        name = name,
        description = description
    ), HasGeometryProperties, CanMoving

    data class Transition(
        override val id: UUID = UUID.randomUUID(),
        override val name: String,
        override val description: String,
        override var centerPoint: Point2D,
        override val size: Size2D,
        override val isMoving: Boolean = false,
        override val orientation: Orientation
    ) : WorkspaceObject(
        id = id,
        name = name,
        description = description
    ), HasGeometryProperties, CanMoving

    data class Arc(
        override val id: UUID = UUID.randomUUID(),
        override val name: String,
        override val description: String,
        val source: ArcSource,
        val points: List<Point2D>,
        val receiver: ArcReceiver,
        val type: ArcType
    ) : WorkspaceObject(
        id = id,
        name = name,
        description = description
    )
}

data class ArcSource(
    val id: UUID
)

sealed class ArcReceiver {
    data class Point(val point: Point2D) : ArcReceiver()
    data class Uuid(val uuid: UUID) : ArcReceiver()
}

enum class ArcType {
    FROM_POSITION,
    FROM_TRANSITION
}

fun workspacePositionOf(
    index: Long,
    point: Point2D,
    name: String = "P${index + 1}",
    description: String = "",
    tokensNumber: Int = 0,
    orientation: Orientation
): WorkspaceObject.Position {
    return WorkspaceObject.Position(
        centerPoint = point,
        size = size2D(BuildConfig.POSITION_DIAMETER),
        name = name,
        description = description,
        tokensNumber = tokensNumber,
        orientation = orientation
    )
}

fun workspaceTransitionOf(
    index: Long,
    name: String = "T${index + 1}",
    description: String = "",
    point: Point2D,
    orientation: Orientation
): WorkspaceObject.Transition {
    return WorkspaceObject.Transition(
        centerPoint = point,
        size = getTransitionSize(orientation),
        name = name,
        description = description,
        orientation = orientation
    )
}

fun workspaceArcOf(
    index: Long,
    source: ArcSource,
    receiver: ArcReceiver,
    type: ArcType
): WorkspaceObject.Arc {
    return WorkspaceObject.Arc(
        source = source,
        points = listOf(),
        receiver = receiver,
        type = type,
        name = "",
        description = ""
    )
}

fun WorkspaceObject.Position.toPnPosition(): PnPosition {
    return PnPosition(
        id = id,
        point = centerPoint,
        size = size,
        name = name,
        description = description,
        tokensNumber = tokensNumber
    )
}

fun WorkspaceObject.Transition.toPnTransition(): PnTransition {
    return PnTransition(
        id = id,
        point = centerPoint,
        size = size,
        name = name,
        description = description
    )
}

fun WorkspaceObject.Arc.toPnArc(getObject: (id: UUID) -> WorkspaceObject?): PnArc? {
    val startPoint = getArcPoint(
        obj = getObject.invoke(source.id),
        pointType = WorkspaceArcPointType.START
    )

    val endPoint = when (receiver) {
        is ArcReceiver.Point -> receiver.point
        is ArcReceiver.Uuid -> {
            getArcPoint(
                obj = getObject.invoke(receiver.uuid),
                pointType = WorkspaceArcPointType.END
            )
        }
    }

    return if (startPoint != null && endPoint != null) {
        PnArc(
            id = id,
            points = mutableListOf<Point2D>().apply {
                add(startPoint)
                addAll(points)
                add(endPoint)
            }
        )
    } else {
        null
    }
}

fun WorkspaceObject.containsPoint(target: Point2D): Boolean {
    return when (this) {
        is HasGeometryProperties -> {
            rectangle2D(
                point = point2D(
                    x = centerPoint.x - size.width / 2,
                    y = centerPoint.y - size.height / 2
                ),
                size = size
            ).contains(target)
        }
        else -> false
    }
}

private fun getArcPoint(
    obj: WorkspaceObject?,
    pointType: WorkspaceArcPointType
): Point2D? {
    val properties = (obj as? HasGeometryProperties)

    return if (properties != null) {
        val orientation = properties.orientation
        val size = obj.size

        val isPosition = obj is WorkspaceObject.Position
        val isTransition = obj is WorkspaceObject.Transition

        val distanceFromCenter = when {
            isPosition -> obj.size.width
            isTransition && orientation == Orientation.HORIZONTAL -> size.width
            isTransition && orientation == Orientation.VERTICAL -> size.height
            else -> throw IllegalStateException()
        } / 2

        val centerPoint = properties.centerPoint
        val x = centerPoint.x
        val y = centerPoint.y

        val isStart = pointType == WorkspaceArcPointType.START
        val isEnd = pointType == WorkspaceArcPointType.END

        val isVertical = orientation == Orientation.VERTICAL
        val isHorizontal = orientation == Orientation.HORIZONTAL

        point2D(
            x = when {
                isStart && isHorizontal -> {
                    x + distanceFromCenter
                }
                isEnd && isHorizontal -> {
                    x - distanceFromCenter
                }
                else -> x
            },
            y = when {
                isStart && isVertical -> {
                    y + distanceFromCenter
                }
                isEnd && isVertical -> {
                    y - distanceFromCenter
                }
                else -> y
            }
        )
    } else {
        null
    }
}

private enum class WorkspaceArcPointType {
    START,
    END
}