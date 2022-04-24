package ru.apetnet.desktop.ui.controller.matrix.exportmatrix

import com.google.inject.Inject
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import ru.apetnet.desktop.domain.service.exportmatrix.ExportMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.matrix.exportmatrix.ExportMatrixState
import ru.apetnet.desktop.domain.ui.matrix.exportmatrix.toState
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import ru.apetnet.desktop.service.matrix.exportmatrix.ExportMatrixService
import ru.apetnet.desktop.ui.controller.matrix.exportmatrix.ExportMatrixController.Companion.TAG
import ru.apetnet.desktop.ui.parent.ParentController
import ru.apetnet.desktop.util.log.safeLog
import tornadofx.fail
import tornadofx.objectProperty
import tornadofx.observableListOf
import tornadofx.success

class ExportMatrixControllerImpl @Inject constructor(
    private val service: ExportMatrixService
) : ParentController(), ExportMatrixController {
    override val tableMatI: ObservableList<MatrixRowState> = observableListOf()
    override val tableMatO: ObservableList<MatrixRowState> = observableListOf()
    override val tableMatC: ObservableList<MatrixRowState> = observableListOf()
    override val tableMarking: ObservableList<MatrixRowState> = observableListOf()

    override val stateProperty: ObjectProperty<ExportMatrixState> = objectProperty()

    override val progressProperty: ReadOnlyBooleanProperty get() = controllerProgressProperty

    override val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>
        get() = controllerErrorProperty

    override fun refreshMatrix(items: List<WorkspaceObjectBrief>) {
        startProgress()
        runAsync {
            service.collectExportMatrix(items)
        } success {
            stopProgress()
            setTablesData(it)
            setState(it)
        } fail {
            stopProgress()
            setError(it)
        }
    }

    override fun reset() {
        listOf(
            tableMatI,
            tableMatO,
            tableMatC,
            tableMarking
        ).forEach {
            it.clear()
        }
    }

    private fun setTablesData(result: ExportMatrixResult) {
        safeLog(result)
        tableMatI.setAll(
            result.matI
        )

        tableMatO.setAll(
            result.matO
        )

        tableMatC.setAll(
            result.matC
        )

        tableMarking.setAll(
            result.marking
        )
    }

    private fun setState(result: ExportMatrixResult) {
        stateProperty.value = result.toState()
    }

    private fun safeLog(result: ExportMatrixResult) {
        safeLog(TAG) { result.matI }
        safeLog(TAG) { result.matO }
        safeLog(TAG) { result.matC }
        safeLog(TAG) { result.marking }
    }
}