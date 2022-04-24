package ru.apetnet.desktop.navigation

import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import java.io.File
import java.util.*

sealed class NavArguments {
    data class Workspace(
        val id: UUID,
        val file: File?,
        val title: String,
        val objects: List<WorkspaceObject> = listOf()
    ) : NavArguments()

    data class ShowMatrix(
        val items: List<WorkspaceObjectBrief>
    ) : NavArguments()

    data class ReachabilityTree(
        val items: List<WorkspaceObjectBrief>
    ) : NavArguments()

    data class MatrixAnalysis(
        val items: List<WorkspaceObjectBrief>
    )
}
