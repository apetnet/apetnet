package ru.apetnet.desktop.domain.ui.matrix

import ru.apetnet.desktop.domain.ui.workspace.items.ArcReceiver
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import java.util.*

sealed class WorkspaceObjectBrief(
    open val id: UUID,
    open val name: String,
    open val description: String
) {
    data class Position(
        override val id: UUID,
        override val name: String,
        override val description: String,
        val tokensNumber: Int
    ) : WorkspaceObjectBrief(
        id = id,
        name = name,
        description = description
    )

    data class Transition(
        override val id: UUID,
        override val name: String,
        override val description: String
    ) : WorkspaceObjectBrief(
        id = id,
        name = name,
        description = description
    )

    data class Arc(
        override val id: UUID,
        override val name: String,
        override val description: String,
        val source: UUID,
        val receiver: UUID
    ) : WorkspaceObjectBrief(
        id = id,
        name = name,
        description = description
    )
}

fun WorkspaceObject.toBrief(): WorkspaceObjectBrief? {
    return when (this) {
        is WorkspaceObject.Position -> mapPositionToBrief(this)
        is WorkspaceObject.Transition -> mapTransitionToBrief(this)
        is WorkspaceObject.Arc -> mapArcToBrief(this)
    }
}

private fun mapPositionToBrief(position: WorkspaceObject.Position): WorkspaceObjectBrief.Position {
    return WorkspaceObjectBrief.Position(
        id = position.id,
        name = position.name,
        description = position.description,
        tokensNumber = position.tokensNumber
    )
}

private fun mapTransitionToBrief(transition: WorkspaceObject.Transition): WorkspaceObjectBrief.Transition {
    return WorkspaceObjectBrief.Transition(
        id = transition.id,
        name = transition.name,
        description = transition.description,
    )
}

private fun mapArcToBrief(arc: WorkspaceObject.Arc): WorkspaceObjectBrief.Arc? {
    val receiverId = (arc.receiver as? ArcReceiver.Uuid)?.uuid
    return if (receiverId != null) {
        WorkspaceObjectBrief.Arc(
            id = arc.id,
            name = arc.name,
            description = arc.description,
            source = arc.source.id,
            receiver = receiverId
        )
    } else {
        null
    }
}