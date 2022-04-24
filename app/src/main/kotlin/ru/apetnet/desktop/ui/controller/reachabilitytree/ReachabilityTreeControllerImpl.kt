package ru.apetnet.desktop.ui.controller.reachabilitytree

import com.google.inject.Inject
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import ru.apetnet.desktop.domain.service.reachabilitytree.ReachabilityTreeResult
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import ru.apetnet.desktop.domain.ui.reachabilitytree.ReachabilityTreeState
import ru.apetnet.desktop.domain.ui.reachabilitytree.toState
import ru.apetnet.desktop.service.matrix.reachabilitytree.ReachabilityTreeService
import ru.apetnet.desktop.ui.controller.reachabilitytree.ReachabilityTreeController.Companion.TAG
import ru.apetnet.desktop.ui.parent.ParentController
import ru.apetnet.desktop.util.log.safeLog
import tornadofx.objectProperty
import tornadofx.observableListOf
import tornadofx.success

class ReachabilityTreeControllerImpl @Inject constructor(
    private val service: ReachabilityTreeService
) : ParentController(), ReachabilityTreeController {
    override val tableConnections: ObservableList<MatrixRowState> = observableListOf()
    override val tableMarkings: ObservableList<MatrixRowState> = observableListOf()

    override val stateProperty: ObjectProperty<ReachabilityTreeState> = objectProperty()

    override val progressProperty: ReadOnlyBooleanProperty get() = controllerProgressProperty

    override val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>
        get() = controllerErrorProperty

    override fun refreshTree(items: List<WorkspaceObjectBrief>) {
        startProgress()

        runAsync {
            service.collectReachabilityTree(items)
        } success {
            safeLog(TAG) { it }
            stopProgress()
            setTableData(it)
            setState(it)
        }
    }

    override fun reset() {
        tableConnections.clear()
        tableMarkings.clear()
    }

    private fun setTableData(result: ReachabilityTreeResult) {
        tableConnections.setAll(
            result.transitions.rowList
        )
        tableMarkings.setAll(
            result.markings.rowList
        )
    }

    private fun setState(result: ReachabilityTreeResult) {
        stateProperty.value = result.toState()
    }
}