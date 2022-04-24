package ru.apetnet.desktop.model

import ru.apetnet.desktop.domain.service.matrixanalysis.MurataAnalysisSolution

data class MockedMurataSolution(
    val colNamesX: List<String>,
    val colNamesY: List<String>,
    val solutionX: List<Int>,
    val solutionY: List<Int>,
    val isConsistent: Boolean,
    val isConservative: Boolean
)

fun MockedMurataSolution.toAnalysisSolution(): MurataAnalysisSolution {
    return MurataAnalysisSolution(
        colNamesX = colNamesX,
        colNamesY = colNamesY,
        solutionX = listOf(solutionX).toRowStateMatrix(),
        solutionY = listOf(solutionY).toRowStateMatrix(),
        isConsistent = isConsistent,
        isConservative = isConservative
    )
}