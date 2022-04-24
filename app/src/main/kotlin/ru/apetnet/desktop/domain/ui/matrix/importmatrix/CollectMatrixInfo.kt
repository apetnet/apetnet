package ru.apetnet.desktop.domain.ui.matrix.importmatrix

import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState

sealed class CollectMatrixInfo(open val marking: List<MatrixRowState>) {
    data class IO(
        val matI: List<MatrixRowState>,
        val matO: List<MatrixRowState>,
        override val marking: List<MatrixRowState>
    ) : CollectMatrixInfo(marking)

    data class C(
        val matC: List<MatrixRowState>,
        override val marking: List<MatrixRowState>
    ) : CollectMatrixInfo(marking)
}
