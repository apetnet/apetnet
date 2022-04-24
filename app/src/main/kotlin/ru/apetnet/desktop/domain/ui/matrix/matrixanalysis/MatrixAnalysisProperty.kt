package ru.apetnet.desktop.domain.ui.matrix.matrixanalysis

sealed class MatrixAnalysisProperty(open val flag: Boolean) {
    data class Conservative(override val flag: Boolean) : MatrixAnalysisProperty(flag)
    data class Consistent(override val flag: Boolean) : MatrixAnalysisProperty(flag)
}