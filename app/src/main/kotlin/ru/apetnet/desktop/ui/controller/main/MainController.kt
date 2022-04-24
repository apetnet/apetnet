package ru.apetnet.desktop.ui.controller.main

import javafx.beans.property.*
import javafx.geometry.Point2D
import ru.apetnet.desktop.domain.service.matrixview.ImportedProjectInfo
import ru.apetnet.desktop.domain.service.storage.LoadedProjectInfo
import ru.apetnet.desktop.domain.service.importmatrix.ImportMatrixResult
import ru.apetnet.desktop.domain.ui.main.AnalysisDialogInfo
import ru.apetnet.desktop.domain.ui.workspace.WorkspaceOrientation
import ru.apetnet.desktop.domain.ui.main.WorkspaceTool
import ru.apetnet.desktop.domain.ui.workspace.analysis.WorkspaceAnalysisData
import ru.apetnet.desktop.domain.ui.workspace.matrix.WorkspaceMatrixInputData
import ru.apetnet.ext.fx.java.geometry.Scale
import java.io.File

interface MainController {
    companion object {
        const val TAG = "MainController"
        val WORKSPACE_ORIENTATION = WorkspaceOrientation.Horizontal
        val WORKSPACE_TOOL = WorkspaceTool.Cursor
    }

    val hasTabAtTapPaneProperty: ReadOnlyBooleanProperty
    val workspaceToolProperty: ObjectProperty<WorkspaceTool>
    val workspaceOrientationProperty: ObjectProperty<WorkspaceOrientation>
    val workspaceScaleProperty: ReadOnlyObjectProperty<Scale>
    val workspaceMousePositionProperty: ReadOnlyObjectProperty<Point2D>

    val showAnalysisDialogProperty: ReadOnlyObjectProperty<AnalysisDialogInfo>

    val canUndoProperty: ReadOnlyBooleanProperty
    val canRedoProperty: ReadOnlyBooleanProperty
    val canSaveProperty: BooleanProperty

    val workspaceTool: WorkspaceTool
    val workspaceOrientation: WorkspaceOrientation

    fun setWorkspaceTool(tool: WorkspaceTool?)

    fun setWorkspaceOrientation(orientation: WorkspaceOrientation?)

    fun onSelectedIndexChanged(selectedIndex: Int)

    fun bindWorkspaceMousePosition(observable: ReadOnlyObjectProperty<Point2D>)

    fun bindWorkspaceToolProperty(observable: ReadOnlyObjectProperty<WorkspaceTool>)

    fun bindWorkspaceScaleProperty(observable: ReadOnlyObjectProperty<Scale>)

    fun bindCanUndoProperty(observable: ReadOnlyBooleanProperty)

    fun bindCanRedoProperty(observable: ReadOnlyBooleanProperty)

    fun bindCanSaveProperty(observable: ReadOnlyBooleanProperty)

    fun bindAnalysisProperty(observable: ReadOnlyObjectProperty<WorkspaceAnalysisData>)

    fun loadProject(file: File): LoadedProjectInfo

    fun importProject(
        data: ImportMatrixResult
    ): ImportedProjectInfo
}