package ru.apetnet.desktop.util.jundo.objects

import javafx.geometry.Orientation
import javafx.geometry.Point2D
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.workspace.items.ArcReceiver
import ru.apetnet.desktop.domain.ui.workspace.items.ArcSource
import ru.apetnet.desktop.domain.ui.workspace.items.ArcType
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import ru.apetnet.desktop.util.config.getTransitionSize
import ru.apetnet.ext.fx.java.geometry.size2D
import java.io.Serializable
import java.math.BigInteger
import java.util.*

sealed class WorkspaceUndoObject(
    open val id: UUID,
    open val name: String,
    open val description: String
) : Serializable {
    data class Position(
        override val id: UUID = UUID.randomUUID(),
        override val name: String,
        override val description: String,
        val tokensNumber: Int,
        val centerPoint: SerializablePoint2D,
        val orientation: Orientation
    ) : WorkspaceUndoObject(
        id = id,
        name = name,
        description = description
    )

    data class Transition(
        override val id: UUID = UUID.randomUUID(),
        override val name: String,
        override val description: String,
        val centerPoint: SerializablePoint2D,
        val orientation: Orientation
    ) : WorkspaceUndoObject(
        id = id,
        name = name,
        description = description
    )

    data class Arc(
        override val id: UUID = UUID.randomUUID(),
        override val name: String,
        override val description: String,
        val source: UUID,
        val points: List<SerializablePoint2D>,
        val receiver: UUID,
        val type: ArcType
    ) : WorkspaceUndoObject(
        id = id,
        name = name,
        description = description
    )
}

fun WorkspaceUndoObject.toObject(): WorkspaceObject {
    return when (this) {
        is WorkspaceUndoObject.Position -> WorkspaceObject.Position(
            id = id,
            name = name,
            description = description,
            centerPoint = centerPoint.toPoint2D(),
            tokensNumber = tokensNumber,
            size = size2D(BuildConfig.POSITION_DIAMETER),
            orientation = orientation
        )
        is WorkspaceUndoObject.Transition -> WorkspaceObject.Transition(
            id = id,
            name = name,
            description = description,
            centerPoint = centerPoint.toPoint2D(),
            size = getTransitionSize(orientation),
            orientation = orientation
        )
        is WorkspaceUndoObject.Arc -> WorkspaceObject.Arc(
            id = id,
            name = name,
            description = description,
            source = ArcSource(source),
            points = points.map { it.toPoint2D() },
            receiver = ArcReceiver.Uuid(receiver),
            type = type
        )
    }
}

fun WorkspaceObject.toUndoObject(): WorkspaceUndoObject? {
    return when {
        this is WorkspaceObject.Position -> {
            WorkspaceUndoObject.Position(
                id = id,
                centerPoint = centerPoint.toSerializable(),
                tokensNumber = tokensNumber,
                name = name,
                description = description,
                orientation = orientation
            )
        }
        this is WorkspaceObject.Transition -> {
            WorkspaceUndoObject.Transition(
                id = id,
                centerPoint = centerPoint.toSerializable(),
                name = name,
                description = description,
                orientation = orientation
            )
        }
        this is WorkspaceObject.Arc && receiver is ArcReceiver.Uuid -> {
            WorkspaceUndoObject.Arc(
                id = id,
                source = source.id,
                points = points.map { it.toSerializable() },
                receiver = receiver.uuid,
                type = type,
                name = name,
                description = description
            )
        }
        else -> null
    }
}

data class SerializablePoint2D(
    val x: Double,
    val y: Double
) : Serializable

internal fun Point2D.toSerializable(): SerializablePoint2D {
    return SerializablePoint2D(
        x = x,
        y = y
    )
}

internal fun SerializablePoint2D.toPoint2D(): Point2D {
    return Point2D(
        x,
        y
    )
}