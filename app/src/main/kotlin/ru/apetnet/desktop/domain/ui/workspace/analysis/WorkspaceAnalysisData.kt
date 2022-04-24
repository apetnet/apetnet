package ru.apetnet.desktop.domain.ui.workspace.analysis

import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.workspace.matrix.WorkspaceMatrixInputData
import java.time.Instant

data class WorkspaceAnalysisData(
    val timestamp: Instant,
    val type: Type,
    val items: List<WorkspaceObjectBrief>
) {
    enum class Type {
        EXPORT_MATRIX,
        REACHABILITY_TREE,
        MATRIX_ANALYSIS
    }
}

fun mapInputDataToAnalysisData(
    data: WorkspaceMatrixInputData,
    type: WorkspaceAnalysisData.Type
): WorkspaceAnalysisData {
    return WorkspaceAnalysisData(
        timestamp = data.timestamp,
        items = data.items,
        type = type
    )
}