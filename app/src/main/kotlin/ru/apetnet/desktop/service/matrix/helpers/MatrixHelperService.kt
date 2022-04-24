package ru.apetnet.desktop.service.matrix.helpers

import ru.apetnet.desktop.domain.service.exportmatrix.IncidenceMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief

interface MatrixHelperService {
    fun collectIncidenceMatrix(
        items: List<WorkspaceObjectBrief>,
        positions: List<WorkspaceObjectBrief.Position>,
        transitions: List<WorkspaceObjectBrief.Transition>,
        arcs: List<WorkspaceObjectBrief.Arc>
    ): IncidenceMatrixResult

    fun collectIncidenceMatrix(
        items: List<WorkspaceObjectBrief>,
    ): IncidenceMatrixResult
}