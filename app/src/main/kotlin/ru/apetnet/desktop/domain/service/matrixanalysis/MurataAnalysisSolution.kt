package ru.apetnet.desktop.domain.service.matrixanalysis

import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState

data class MurataAnalysisSolution(
    val colNamesX: List<String>,
    val colNamesY: List<String>,
    val solutionX: List<MatrixRowState>,
    val solutionY: List<MatrixRowState>,
    val isConservative: Boolean,
    val isConsistent: Boolean
)