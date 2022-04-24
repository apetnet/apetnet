package ru.apetnet.desktop.service.matrix.exportmatrix

import ru.apetnet.desktop.domain.service.exportmatrix.ExportMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief

interface ExportMatrixService {
    fun collectExportMatrix(
        items: List<WorkspaceObjectBrief>
    ): ExportMatrixResult
}