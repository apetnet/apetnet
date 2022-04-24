package ru.apetnet.desktop.domain.ui.workspace.editobject

import javafx.geometry.Point2D
import java.math.BigInteger
import java.util.*

sealed class WorkspaceEditObjectState(
    open val id: UUID,
    open val name: String,
    open val description: String,
    open val point: Point2D
) {
    data class Position(
        override val id: UUID,
        override val name: String,
        override val description: String,
        override val point: Point2D,
        val tokenNumber: Int
    ) : WorkspaceEditObjectState(
        id = id,
        name = name,
        description = description,
        point = point
    )

    data class Transition(
        override val id: UUID,
        override val name: String,
        override val description: String,
        override val point: Point2D
    ) : WorkspaceEditObjectState(
        id = id,
        name = name,
        description = description,
        point = point
    )
}