package ru.apetnet.desktop.ui.controller.workspace

import com.gdetotut.jundo.UndoStack
import com.google.inject.Inject
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.geometry.Point2D
import javafx.scene.Cursor
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.service.workspace.WorkspaceObjectModel
import ru.apetnet.desktop.domain.ui.main.WorkspaceTool
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import ru.apetnet.desktop.domain.ui.workspace.*
import ru.apetnet.desktop.domain.ui.workspace.analysis.WorkspaceAnalysisData
import ru.apetnet.desktop.domain.ui.workspace.analysis.mapInputDataToAnalysisData
import ru.apetnet.desktop.domain.ui.workspace.editobject.*
import ru.apetnet.desktop.domain.ui.workspace.items.*
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import ru.apetnet.desktop.service.workspace.PnWorkspaceService
import ru.apetnet.desktop.ui.parent.ParentController
import ru.apetnet.desktop.util.jundo.UndoId
import ru.apetnet.desktop.util.jundo.WorkspaceUndoCommand
import ru.apetnet.ext.fx.java.geometry.Scale
import ru.apetnet.ext.fx.java.scene.canvas.ScaleOperation
import ru.apetnet.ext.fx.tornado.onChangeDetail
import tornadofx.*
import java.io.File
import java.time.Instant
import java.util.*

class WorkspaceControllerImpl @Inject constructor(
    private val service: PnWorkspaceService
) : ParentController(), WorkspaceController {
    private val stack = UndoStack(this, null).also {
        it.watcher = this
        it.localContexts[UndoId.IDS_LISTENER] = service
    }

    override val state: WorkspaceObjectState
        get() = mapToWorkspaceObjectModel(
            model = service.workspaceObjectModel
        )

    override val scaleProperty: ObjectProperty<Scale> = objectProperty(Scale())
    override val mousePositionProperty: ObjectProperty<Point2D> = objectProperty(null)
    override val cursorProperty: ObjectProperty<Cursor> = objectProperty(Cursor.DEFAULT)
    override val contextMenuStateProperty: ObjectProperty<WorkspaceEditObjectState> = objectProperty(null)
    override val workspaceProjectDataProperty: ObjectProperty<WorkspaceProjectData> = objectProperty(null)
    override val selectedToolProperty: ObjectProperty<WorkspaceTool> = objectProperty(null)
    override val workspaceEditOperationStateProperty: ObjectProperty<WorkspaceEditOperationState> = objectProperty(null)
    override val workspaceAnalysisProperty: ObjectProperty<WorkspaceAnalysisData> = objectProperty(null)
    override val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>
        get() = controllerErrorProperty

    override val progressProperty: ReadOnlyBooleanProperty
        get() = controllerProgressProperty

    override val canUndoProperty: BooleanProperty = booleanProperty(false)
    override val canRedoProperty: BooleanProperty = booleanProperty(false)
    override val canSaveProperty: BooleanProperty = booleanProperty(false)

    /*
     * Private
     */
    private val contextMenuState: WorkspaceEditObjectState? get() = contextMenuStateProperty.value
    private val currentScale: Scale get() = scaleProperty.value
    private val workspaceProjectData: WorkspaceProjectData? get() = workspaceProjectDataProperty.value

    private val selectedTool: WorkspaceTool get() = selectedToolProperty.value
    private lateinit var selectedOrientation: WorkspaceOrientation

    private var selectedObject: WorkspaceSelectedObject? = null

    private lateinit var selectedToolBeforeMove: WorkspaceTool

    init {
        indexChanged(stack.idx)
        subscribeToWorkspaceObjectProperties()
    }

    override fun setWorkspaceTool(tool: WorkspaceTool) {
        selectedToolProperty.value = tool
    }

    override fun setWorkspaceOrientation(orientation: WorkspaceOrientation) {
        selectedOrientation = orientation
    }

    override fun setProjectData(
        id: UUID,
        title: String,
        file: File?,
        objects: List<WorkspaceObject>
    ) {
        setWorkspaceProjectData(
            data = WorkspaceProjectData(
                id = id,
                title = title,
                instant = Instant.now(),
                file = file
            )
        )
        service.setObjects(objects)
    }

    override fun onMoved(point: Point2D) {
        mousePositionProperty.value = point

        if (selectedTool == WorkspaceTool.AddArc) {
            service.setArcDirectionPoint(point)
        }
    }

    override fun onPrimaryMouseClicked(point: Point2D) {
        when {
            contextMenuState != null -> {
                contextMenuStateProperty.value = null
            }
            selectedTool == WorkspaceTool.AddPosition -> {
                service.addPosition(
                    point = point,
                    orientation = selectedOrientation.toOrientation()
                )
            }
            selectedTool == WorkspaceTool.AddTransition -> {
                service.addTransition(
                    point = point,
                    orientation = selectedOrientation.toOrientation()
                )
            }
            selectedTool == WorkspaceTool.AddArc -> {
                service.addArc(
                    point = point
                )
            }
            selectedTool == WorkspaceTool.Delete -> {
                service.deleteObject(
                    point = point
                )
            }
            selectedTool == WorkspaceTool.SetToken -> {
                setWorkspaceEditOperationState(
                    operation = WorkspaceEditOperation.SetToken,
                    point = point
                )
            }
            selectedTool == WorkspaceTool.Rename -> {
                setWorkspaceEditOperationState(
                    operation = WorkspaceEditOperation.Rename,
                    point = point
                )
            }
            selectedTool == WorkspaceTool.Rotate -> {
                service.rotateObject(
                    point = point
                )
            }
        }
        resetSelectedTool()
    }

    override fun onMiddleMousePressed(point: Point2D) {
        cursorProperty.value = Cursor.MOVE
    }

    override fun onMiddleMouseReleased(point: Point2D) {
        resetCursor()
    }

    override fun onPrimaryMouseDragged(point: Point2D) {
        moveObject(point)
    }

    override fun changeScale(operation: ScaleOperation) {
        val scaleFactor = BuildConfig.SCALE_FACTOR
        var scale = currentScale.value

        when {
            operation == ScaleOperation.SCALE_IN && scale < 5 -> {
                scale += scaleFactor
            }
            operation == ScaleOperation.SCALE_OUT && scale > 1 -> {
                scale -= scaleFactor
            }
        }

        scaleProperty.value = Scale(
            value = scale.coerceIn(BuildConfig.MIN_SCALE, BuildConfig.MAX_SCALE)
        )
    }

    override fun onContextMenuRequested(
        clickPoint: Point2D,
        screenPoint: Point2D
    ) {
        val state = service.getWorkspaceEditObjectState(
            clickPoint = clickPoint,
            actualPoint = screenPoint
        )

        contextMenuStateProperty.value = state
    }

    override fun onObjectOperationClicked(
        operation: WorkspaceObjectEditOperation,
    ) {
        when (operation) {
            is WorkspaceObjectEditOperation.Delete -> service.deleteObject(
                id = operation.id
            )
            is WorkspaceObjectEditOperation.SetToken -> service.setTokenNumber(
                id = operation.id,
                number = operation.tokenNumber
            )
            is WorkspaceObjectEditOperation.Rename -> service.setObjectName(
                id = operation.id,
                name = operation.name
            )
            is WorkspaceObjectEditOperation.Description -> service.setObjectDescription(
                id = operation.id,
                description = operation.description
            )
            is WorkspaceObjectEditOperation.Rotate -> service.rotateObject(
                id = operation.id
            )
        }

        resetContextMenuState()
    }

    override fun onRedoClicked() {
        stack.redo()
    }

    override fun onUndoClicked() {
        stack.undo()
    }

    override fun onCleanClicked() {
        service.deleteObjects()
        stack.clear()
    }

    override fun resetCurrentFocus() {
        service.resetCurrentFocus()
    }

    override fun indexChanged(idx: Int) {
        canUndoProperty.value = stack.canUndo()
        canRedoProperty.value = stack.canRedo()
    }

    override fun saveProject() {
        val file = workspaceProjectData?.file

        if (file != null) {
            saveProjectAs(file)
        }
    }

    override fun saveProjectAs(file: File) {
        val projectData = workspaceProjectData

        if (projectData != null) {
            val data = service.saveProject(
                file = file,
                projectId = projectData.id
            )

            setWorkspaceProjectData(
                data = projectData.copy(
                    title = data.fileName,
                    file = file
                )
            )
        }
    }

    override fun showMatrix() {
        showAnalysisInfo(
            type = WorkspaceAnalysisData.Type.EXPORT_MATRIX
        )
    }

    override fun showReachabilityTree() {
        showAnalysisInfo(
            type = WorkspaceAnalysisData.Type.REACHABILITY_TREE
        )
    }

    override fun showMatrixAnalysisSolution() {
        showAnalysisInfo(
            type = WorkspaceAnalysisData.Type.MATRIX_ANALYSIS
        )
    }

    private fun showAnalysisInfo(type: WorkspaceAnalysisData.Type) {
        startProgress()

        runAsync {
            service.collectMatrixBrief()
        } success  { data ->
            stopProgress()
            workspaceAnalysisProperty.value = mapInputDataToAnalysisData(
                data = data,
                type = type
            )
        } fail {
            stopProgress()
            setError(it)
        }
    }

    private fun setWorkspaceProjectData(data: WorkspaceProjectData) {
        workspaceProjectDataProperty.value = data
        canSaveProperty.value = (workspaceProjectData?.file != null)
    }

    private fun setWorkspaceEditOperationState(
        operation: WorkspaceEditOperation,
        point: Point2D
    ) {
        val objectState = service.getWorkspaceEditObjectState(point)

        if (objectState != null) {
            workspaceEditOperationStateProperty.value = mapToWorkspaceEditOperationState(
                operation = operation,
                objectState = objectState
            )
        }
    }

    private fun resetCursor() {
        cursorProperty.value = Cursor.DEFAULT
    }

    private fun moveObject(point: Point2D) {
        if (selectedObject == null) {
            selectedToolBeforeMove = selectedTool
            setSelectedObject(point)
        }

        val id = selectedObject?.id

        if (id != null) {
            setWorkspaceTool(WorkspaceTool.Move)

            service.moveObject(
                id = id,
                point = point
            )
        }
    }

    private fun resetSelectedTool() {
        val selected = selectedObject

        if (selected != null) {
            service.stopObjectMoving(selected.id)
            selectedObject = null

            if (::selectedToolBeforeMove.isInitialized) {
                setWorkspaceTool(selectedToolBeforeMove)
            }
        }
    }

    private fun resetContextMenuState() {
        contextMenuStateProperty.value = null
    }

    private fun mapToWorkspaceObjectModel(model: WorkspaceObjectModel): WorkspaceObjectState {
        val objects = model.objects

        val items = objects.values.map { obj ->
            when (obj) {
                is WorkspaceObject.Position -> obj.toPnPosition()
                is WorkspaceObject.Transition -> obj.toPnTransition()
                is WorkspaceObject.Arc -> obj.toPnArc { objects[it] }
            }
        }

        return WorkspaceObjectState(
            timestamp = model.timestamp,
            objects = items.filterNotNull()
        )
    }

    private fun setSelectedObject(point: Point2D) {
        val id = service.findIdByPoint(point)
        selectedObject = id?.let(::workspaceSelectedObjectOf)
    }

    private fun subscribeToWorkspaceObjectProperties() {
        service.workspaceUndoObjectMapProperty.onChangeDetail { oldValue, newValue ->
            stack.push(
                WorkspaceUndoCommand.AddObject(
                    owner = stack,
                    parent = null,
                    old = oldValue.orEmpty(),
                    new = newValue.orEmpty()
                )
            )
        }
    }
}