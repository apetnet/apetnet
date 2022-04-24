package ru.apetnet.desktop.model

import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState

internal sealed class MockedMatrixIO {
    data class RowState(
        val matI: List<MatrixRowState>,
        val matO: List<MatrixRowState>,
        val matC: List<MatrixRowState>,
        val matMarking: List<MatrixRowState>,
    )

    data class Simple(
        val matI: SimpleMatrix,
        val matO: SimpleMatrix,
        val matC: SimpleMatrix,
        val matMarking: SimpleMatrix,
    )
}