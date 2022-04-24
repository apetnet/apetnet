package ru.apetnet.desktop.domain.ui.matrix.matrixanalysis

import ru.apetnet.desktop.domain.service.matrixanalysis.MurataAnalysisSolution

data class MatrixAnalysisState(
    val colNamesX: List<String>,
    val colNamesY: List<String>,
    val properties: List<MatrixAnalysisProperty>,
)

fun MurataAnalysisSolution.toState(): MatrixAnalysisState {
    return MatrixAnalysisState(
        colNamesX = colNamesX,
        colNamesY = colNamesY,
        properties = listOf(
            MatrixAnalysisProperty.Consistent(isConsistent),
            MatrixAnalysisProperty.Conservative(isConservative)
        )
    )
}