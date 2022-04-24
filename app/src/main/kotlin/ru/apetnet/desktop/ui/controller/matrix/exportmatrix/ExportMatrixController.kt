package ru.apetnet.desktop.ui.controller.matrix.exportmatrix

import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.matrix.exportmatrix.ExportMatrixState
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData

interface ExportMatrixController {
    companion object {
        const val TAG = "ExportMatrixController"
    }

    val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>

    val progressProperty: ReadOnlyBooleanProperty
    val stateProperty: ObjectProperty<ExportMatrixState>

    val tableMatI: ObservableList<MatrixRowState>
    val tableMatO: ObservableList<MatrixRowState>
    val tableMatC: ObservableList<MatrixRowState>
    val tableMarking: ObservableList<MatrixRowState>

    fun refreshMatrix(items: List<WorkspaceObjectBrief>)

    fun reset()
}