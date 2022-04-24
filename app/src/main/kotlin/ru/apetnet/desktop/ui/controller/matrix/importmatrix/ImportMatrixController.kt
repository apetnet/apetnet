package ru.apetnet.desktop.ui.controller.matrix.importmatrix

import javafx.beans.property.*
import javafx.collections.ObservableList
import ru.apetnet.desktop.domain.service.importmatrix.ImportMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.importmatrix.ImportMatrixState
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.importmatrix.ImportMatrixOrientation
import ru.apetnet.desktop.domain.ui.matrix.KindIncidenceMatrices
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData

interface ImportMatrixController {
    companion object {
        const val TAG = "ImportMatrixController"
        const val MATRIX_SIZE: Int = 0
        val IMPORT_MATRIX_SOURCE: KindIncidenceMatrices = KindIncidenceMatrices.IO
    }
    val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>

    val progressProperty: ReadOnlyBooleanProperty
    val hasImportDataProperty: ReadOnlyBooleanProperty

    val positionNumberProperty: ObjectProperty<Int>
    val transitionNumberProperty: ObjectProperty<Int>
    val kindIncidenceMatricesProperty: ObjectProperty<KindIncidenceMatrices>
    val importMatrixOrientationProperty: ObjectProperty<ImportMatrixOrientation>

    val stateProperty: ReadOnlyObjectProperty<ImportMatrixState>
    val matrixResultProperty: ReadOnlyObjectProperty<ImportMatrixResult>

    val tableMatI: ObservableList<MatrixRowState>
    val tableMatO: ObservableList<MatrixRowState>
    val tableMatC: ObservableList<MatrixRowState>
    val tableMarking: ObservableList<MatrixRowState>

    fun onPositionNumberChanged(number: Int)
    fun onTransitionNumberChanged(number: Int)
    fun onImportMatrixSourceChanged(source: KindIncidenceMatrices)
    fun setOrientation(orientation: ImportMatrixOrientation)
    fun onImportClicked()
    fun reset()
}