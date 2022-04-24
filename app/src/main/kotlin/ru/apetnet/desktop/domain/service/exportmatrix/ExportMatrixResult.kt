package ru.apetnet.desktop.domain.service.exportmatrix

import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState

data class ExportMatrixResult(
    val positionNames: List<String>,
    val transitionNames: List<String>,

    val matI: List<MatrixRowState>,
    val matO: List<MatrixRowState>,
    val matC: List<MatrixRowState>,
    val marking: List<MatrixRowState>,
)