package ru.apetnet.desktop.ui.controller.matrix.matrixanalysis

import com.google.inject.Inject
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import ru.apetnet.desktop.domain.service.matrixanalysis.MurataAnalysisSolution
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.matrix.matrixanalysis.MatrixAnalysisPropertyItem
import ru.apetnet.desktop.domain.ui.matrix.matrixanalysis.MatrixAnalysisState
import ru.apetnet.desktop.domain.ui.matrix.matrixanalysis.toState
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import ru.apetnet.desktop.service.matrix.matrixanalysis.MurataAnalysisService
import ru.apetnet.desktop.ui.parent.ParentController
import tornadofx.*

class MatrixAnalysisControllerImpl @Inject constructor(
    private val service: MurataAnalysisService
) : ParentController(), MatrixAnalysisController {
    override val tableSolX: ObservableList<MatrixRowState> = observableListOf()
    override val tableSolY: ObservableList<MatrixRowState> = observableListOf()
    override val tableResult: ObservableList<MatrixRowState> = observableListOf()

    override val stateProperty: ObjectProperty<MatrixAnalysisState> = objectProperty()

    override val progressProperty: ReadOnlyBooleanProperty get() = controllerProgressProperty

    override val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>
        get() = controllerErrorProperty

    override fun refreshTable(items: List<WorkspaceObjectBrief>) {
        startProgress()

        runAsync {
            service.collectSolution(items)
        } success {
            stopProgress()
            setTableData(it)
            setState(it)
        } fail {
            stopProgress()
            setError(it)
        }
    }

    override fun setResult(properties: List<MatrixAnalysisPropertyItem>) {
        tableResult.setAll(
            properties.mapIndexed { index, property ->
                MatrixRowState(
                    (index + 1).toString(),
                    listOf(
                        stringProperty(property.name),
                        stringProperty(property.value)
                    )
                )
            }
        )
    }

    private fun setTableData(data: MurataAnalysisSolution) {
        tableSolX.setAll(
            data.solutionX
        )
        tableSolY.setAll(
            data.solutionY
        )
    }

    private fun setState(result: MurataAnalysisSolution) {
        stateProperty.value = result.toState()
    }

    override fun reset() {
        listOf(
            tableSolX,
            tableSolY
        ).forEach {
            it.clear()
        }
    }
}