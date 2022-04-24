package ru.apetnet.desktop.domain.ui.matrix.exportmatrix

import ru.apetnet.desktop.domain.service.exportmatrix.ExportMatrixResult

data class ExportMatrixState(
    val colMarkingNames: List<String>,
    val colMatrixNames: List<String>
)

fun ExportMatrixResult.toState(): ExportMatrixState {
    return ExportMatrixState(
        colMatrixNames = transitionNames,
        colMarkingNames = positionNames
    )
}