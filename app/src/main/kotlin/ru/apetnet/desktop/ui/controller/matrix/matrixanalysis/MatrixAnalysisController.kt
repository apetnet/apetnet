package ru.apetnet.desktop.ui.controller.matrix.matrixanalysis

import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.matrix.matrixanalysis.MatrixAnalysisPropertyItem
import ru.apetnet.desktop.domain.ui.matrix.matrixanalysis.MatrixAnalysisState
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData

interface MatrixAnalysisController {
    companion object {
        const val TAG = "MatrixAnalysisController"
    }

    val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>

    val progressProperty: ReadOnlyBooleanProperty
    val stateProperty: ObjectProperty<MatrixAnalysisState>

    val tableSolX: ObservableList<MatrixRowState>
    val tableSolY: ObservableList<MatrixRowState>
    val tableResult: ObservableList<MatrixRowState>

    fun refreshTable(
        items: List<WorkspaceObjectBrief>
    )

    fun setResult(properties: List<MatrixAnalysisPropertyItem>)

    fun reset()
}