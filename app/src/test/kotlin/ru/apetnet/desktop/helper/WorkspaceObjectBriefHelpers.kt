package ru.apetnet.desktop.helper

import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import java.util.*

fun workspaceObjectBriefPositionOf(
    index: Int,
    tokensNumber: Int = 0
): WorkspaceObjectBrief.Position {
    return WorkspaceObjectBrief.Position(
        id = UUID.randomUUID(),
        name = "P${index + BuildConfig.MATRIX_ROW_OFFSET}",
        tokensNumber = tokensNumber,
        description = ""
    )
}

fun workspaceObjectBriefTransitionOf(
    index: Int
): WorkspaceObjectBrief.Transition {
    return WorkspaceObjectBrief.Transition(
        id = UUID.randomUUID(),
        name = "T${index + BuildConfig.MATRIX_ROW_OFFSET}",
        description = ""
    )
}

fun workspaceObjectBriefArcOf(
    index: Int,
    source: UUID,
    receiver: UUID
): WorkspaceObjectBrief.Arc {
    return WorkspaceObjectBrief.Arc(
        id = UUID.randomUUID(),
        name = "",
        source = source,
        receiver = receiver,
        description = ""
    )
}