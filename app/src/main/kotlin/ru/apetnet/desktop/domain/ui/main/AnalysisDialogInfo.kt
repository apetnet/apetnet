package ru.apetnet.desktop.domain.ui.main

import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import java.time.Instant

sealed class AnalysisDialogInfo(
    open val items: List<WorkspaceObjectBrief>,
    open val timestamp: Instant = Instant.now()
) {
    data class ExportMatrix(
        override val items: List<WorkspaceObjectBrief>,
        override val timestamp: Instant = Instant.now()
    ) : AnalysisDialogInfo(items, timestamp)

    data class ReachabilityTree(
        override val items: List<WorkspaceObjectBrief>,
        override val timestamp: Instant = Instant.now()
    ) : AnalysisDialogInfo(items, timestamp)

    data class MatrixAnalysis(
        override val items: List<WorkspaceObjectBrief>,
        override val timestamp: Instant = Instant.now()
    ) : AnalysisDialogInfo(items, timestamp)
}
