package ru.apetnet.desktop.service.matrix.importmatrix

import javafx.geometry.Orientation
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.service.importmatrix.ImportMatrixResult

interface ImportMatrixService {
    companion object {
        const val TAG = "ImportMatrixHelperService"
    }

    fun collectMatrix(
        list: List<MatrixRowState>,
        rowsNum: Int,
        colsNum: Int,
    ): List<MatrixRowState>

    fun collectResultIO(
        matI: List<MatrixRowState>,
        matO: List<MatrixRowState>,
        matMarking: List<MatrixRowState>,
        orientation: Orientation
    ): ImportMatrixResult

    fun collectResultC(
        matC: List<MatrixRowState>,
        matMarking: List<MatrixRowState>,
        orientation: Orientation
    ): ImportMatrixResult
}