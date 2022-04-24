package ru.apetnet.desktop.ui.controller.reachabilitytree

import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import ru.apetnet.desktop.domain.ui.reachabilitytree.ReachabilityTreeState

interface ReachabilityTreeController {
    companion object {
        const val TAG = "ReachabilityTreeController"
    }

    val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>
    val progressProperty: ReadOnlyBooleanProperty

    val stateProperty: ObjectProperty<ReachabilityTreeState>

    val tableConnections: ObservableList<MatrixRowState>
    val tableMarkings: ObservableList<MatrixRowState>

    fun refreshTree(items: List<WorkspaceObjectBrief>)

    fun reset()
}