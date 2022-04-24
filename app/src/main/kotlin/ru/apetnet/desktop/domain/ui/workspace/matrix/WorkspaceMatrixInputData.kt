package ru.apetnet.desktop.domain.ui.workspace.matrix

import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import java.time.Instant

data class WorkspaceMatrixInputData(
    val timestamp: Instant,
    val items: List<WorkspaceObjectBrief>
)