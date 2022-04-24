package ru.apetnet.desktop.ui.controller.workspace

import com.gdetotut.jundo.UndoWatcher
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.geometry.Point2D
import javafx.scene.Cursor
import ru.apetnet.desktop.domain.ui.main.WorkspaceTool
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import ru.apetnet.desktop.domain.ui.workspace.*
import ru.apetnet.desktop.domain.ui.workspace.analysis.WorkspaceAnalysisData
import ru.apetnet.desktop.domain.ui.workspace.editobject.WorkspaceEditObjectState
import ru.apetnet.desktop.domain.ui.workspace.editobject.WorkspaceEditOperationState
import ru.apetnet.desktop.domain.ui.workspace.editobject.WorkspaceObjectEditOperation
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObjectState
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import ru.apetnet.ext.fx.java.geometry.Scale
import ru.apetnet.ext.fx.java.scene.canvas.ScaleOperation
import java.io.File
import java.util.*

interface WorkspaceController : UndoWatcher {
    companion object {
        const val TAG = "WorkspaceController"
    }

    val state: WorkspaceObjectState

    val canUndoProperty: ReadOnlyBooleanProperty
    val canRedoProperty: ReadOnlyBooleanProperty
    val canSaveProperty: BooleanProperty

    val workspaceProjectDataProperty: ReadOnlyObjectProperty<WorkspaceProjectData>
    val selectedToolProperty: ReadOnlyObjectProperty<WorkspaceTool>
    val scaleProperty: ReadOnlyObjectProperty<Scale>
    val mousePositionProperty: ReadOnlyObjectProperty<Point2D>
    val cursorProperty: ReadOnlyObjectProperty<Cursor>
    val contextMenuStateProperty: ReadOnlyObjectProperty<WorkspaceEditObjectState>
    val workspaceEditOperationStateProperty: ReadOnlyObjectProperty<WorkspaceEditOperationState>
    val workspaceAnalysisProperty: ReadOnlyObjectProperty<WorkspaceAnalysisData>
    val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>

    val progressProperty: ReadOnlyBooleanProperty

    fun setWorkspaceTool(tool: WorkspaceTool)

    fun setProjectData(
        id: UUID,
        title: String,
        file: File?,
        objects: List<WorkspaceObject>
    )

    fun onMoved(point: Point2D)

    fun onPrimaryMouseClicked(point: Point2D)

    fun onMiddleMousePressed(point: Point2D)

    fun onMiddleMouseReleased(point: Point2D)

    fun onPrimaryMouseDragged(point: Point2D)

    fun changeScale(operation: ScaleOperation)

    fun setWorkspaceOrientation(orientation: WorkspaceOrientation)

    fun onContextMenuRequested(
        clickPoint: Point2D,
        screenPoint: Point2D
    )

    fun onObjectOperationClicked(
        operation: WorkspaceObjectEditOperation
    )

    fun onUndoClicked()

    fun onRedoClicked()

    fun onCleanClicked()

    fun resetCurrentFocus()

    fun saveProject()

    fun saveProjectAs(file: File)

    fun showMatrix()

    fun showReachabilityTree()

    fun showMatrixAnalysisSolution()
}