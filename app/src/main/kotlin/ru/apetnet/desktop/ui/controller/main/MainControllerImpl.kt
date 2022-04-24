package ru.apetnet.desktop.ui.controller.main

import com.google.inject.Inject
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.geometry.Point2D
import ru.apetnet.desktop.domain.service.matrixview.ImportedProjectInfo
import ru.apetnet.desktop.domain.service.storage.LoadedProjectInfo
import ru.apetnet.desktop.domain.service.importmatrix.ImportMatrixResult
import ru.apetnet.desktop.domain.ui.main.AnalysisDialogInfo
import ru.apetnet.desktop.domain.ui.workspace.WorkspaceOrientation
import ru.apetnet.desktop.domain.ui.main.WorkspaceTool
import ru.apetnet.desktop.domain.ui.workspace.analysis.WorkspaceAnalysisData
import ru.apetnet.desktop.navigation.NavArguments
import ru.apetnet.desktop.service.matrix.MatrixViewService
import ru.apetnet.desktop.service.storage.StorageService
import ru.apetnet.desktop.ui.controller.main.MainController.Companion.TAG
import ru.apetnet.desktop.util.log.safeLog
import ru.apetnet.ext.fx.java.geometry.Scale
import ru.apetnet.ext.fx.tornado.onNotNullChange
import tornadofx.*
import java.io.File

class MainControllerImpl @Inject constructor(
    private val storageService: StorageService,
    private val matrixViewService: MatrixViewService
) : Controller(), MainController {
    /*
     * Properties
     */
    override val hasTabAtTapPaneProperty: BooleanProperty = booleanProperty(false)

    override val workspaceScaleProperty: ObjectProperty<Scale> = objectProperty(null)
    override val workspaceMousePositionProperty: ObjectProperty<Point2D> = objectProperty()
    override val showAnalysisDialogProperty: ObjectProperty<AnalysisDialogInfo> = objectProperty()
    override val workspaceToolProperty: ObjectProperty<WorkspaceTool> = objectProperty(null)
    override val workspaceOrientationProperty: ObjectProperty<WorkspaceOrientation> = objectProperty(null)
    override val canRedoProperty: BooleanProperty = booleanProperty(false)
    override val canUndoProperty: BooleanProperty = booleanProperty(false)
    override val canSaveProperty: BooleanProperty = booleanProperty(false)

    override val workspaceOrientation: WorkspaceOrientation
        get() = workspaceOrientationProperty.value

    override val workspaceTool: WorkspaceTool
        get() = workspaceToolProperty.value

    private var canRedo: Boolean = false
    private var canUndo: Boolean = false
    private var canSave: Boolean = false

    /*
     * Setters
     */
    override fun setWorkspaceTool(tool: WorkspaceTool?) {
        workspaceToolProperty.value = tool
    }

    override fun setWorkspaceOrientation(orientation: WorkspaceOrientation?) {
        workspaceOrientationProperty.value = orientation
    }

    override fun onSelectedIndexChanged(selectedIndex: Int) {
        hasTabAtTapPaneProperty.value = selectedIndex != -1
        refreshCanUndo()
        refreshCanRedo()
    }

    override fun bindWorkspaceMousePosition(observable: ReadOnlyObjectProperty<Point2D>) {
        workspaceMousePositionProperty.bind(observable)
    }

    override fun bindWorkspaceToolProperty(observable: ReadOnlyObjectProperty<WorkspaceTool>) {
        observable.onChange(::setWorkspaceTool)
    }

    override fun bindWorkspaceScaleProperty(observable: ReadOnlyObjectProperty<Scale>) {
        workspaceScaleProperty.bind(observable)
    }

    override fun bindCanUndoProperty(observable: ReadOnlyBooleanProperty) {
        observable.onChange(::setCanUndo)
        setCanUndo(observable.value)
    }

    override fun bindCanRedoProperty(observable: ReadOnlyBooleanProperty) {
        observable.onChange(::setCanRedo)
        setCanRedo(observable.value)
    }

    override fun bindCanSaveProperty(observable: ReadOnlyBooleanProperty) {
        observable.onChange(::setCanSave)
        setCanSave(observable.value)
    }

    override fun bindAnalysisProperty(observable: ReadOnlyObjectProperty<WorkspaceAnalysisData>) {
        observable.onNotNullChange {
            val items = it.items

            showAnalysisDialogProperty.value = when (it.type) {
                WorkspaceAnalysisData.Type.EXPORT_MATRIX -> {
                    AnalysisDialogInfo.ExportMatrix(items)
                }
                WorkspaceAnalysisData.Type.REACHABILITY_TREE -> {
                    AnalysisDialogInfo.ReachabilityTree(items)
                }
                WorkspaceAnalysisData.Type.MATRIX_ANALYSIS -> {
                    AnalysisDialogInfo.MatrixAnalysis(items)
                }
            }
        }
    }

    override fun loadProject(file: File): LoadedProjectInfo {
        return storageService.loadProject(file)
    }

    override fun importProject(data: ImportMatrixResult): ImportedProjectInfo {
        return matrixViewService.importProject(
            matI = data.matI,
            matO = data.matO,
            matMarking = data.matMarking,
            positionNames = data.positionNames,
            transitionNames = data.transitionNames,
            orientation = data.orientation
        )
    }

    private fun setCanUndo(isCan: Boolean) {
        canUndo = isCan
        refreshCanUndo()
    }

    private fun setCanRedo(isCan: Boolean) {
        canRedo = isCan
        refreshCanRedo()
    }

    private fun setCanSave(isCan: Boolean) {
        canSave = isCan
        refreshCanSave()
    }

    private fun refreshCanRedo() {
        canRedoProperty.value = canRedo && hasTabAtTapPaneProperty.value
    }

    private fun refreshCanUndo() {
        canUndoProperty.value = canUndo && hasTabAtTapPaneProperty.value
    }

    private fun refreshCanSave() {
        canSaveProperty.value = canSave && hasTabAtTapPaneProperty.value
    }
}