package ru.apetnet.desktop.ui.fragment

import javafx.animation.AnimationTimer
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.geometry.Point2D
import javafx.scene.canvas.Canvas
import javafx.scene.control.ContextMenu
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.main.WorkspaceTool
import ru.apetnet.desktop.domain.ui.workspace.WorkspaceOrientation
import ru.apetnet.desktop.domain.ui.workspace.WorkspaceProjectData
import ru.apetnet.desktop.domain.ui.workspace.analysis.WorkspaceAnalysisData
import ru.apetnet.desktop.domain.ui.workspace.editobject.WorkspaceEditObjectState
import ru.apetnet.desktop.domain.ui.workspace.editobject.WorkspaceEditOperationState
import ru.apetnet.desktop.domain.ui.workspace.editobject.WorkspaceObjectEditOperation
import ru.apetnet.desktop.navigation.NavArguments
import ru.apetnet.desktop.navigation.requireArguments
import ru.apetnet.desktop.ui.controller.workspace.WorkspaceController
import ru.apetnet.desktop.ui.listeners.*
import ru.apetnet.desktop.ui.parent.ParentFragment
import ru.apetnet.desktop.util.resource.string
import ru.apetnet.ext.fx.java.geometry.Scale
import ru.apetnet.ext.fx.java.geometry.point2D
import ru.apetnet.ext.fx.java.scene.canvas.ScaleOperation
import ru.apetnet.ext.fx.java.scene.input.toPoint2D
import ru.apetnet.ext.fx.tornado.fieldDialog
import ru.apetnet.ext.fx.tornado.onNotNullChange
import tornadofx.*
import java.io.File
import java.time.Instant

class WorkspaceFragment : ParentFragment(),
    OnClickAtContainerListener,
    HasWorkspaceTool,
    HasWorkspaceOrientation,
    HasCanvasProperties,
    HasWorkspaceProperties,
    HasSelectedToolProperty,
    HasAnalysisProperties {

    companion object {
        const val TAG: String = "WorkspaceFragment"
    }

    override val mousePositionProperty: ReadOnlyObjectProperty<Point2D>
        get() = controller.mousePositionProperty

    override val scaleProperty: ReadOnlyObjectProperty<Scale>
        get() = controller.scaleProperty

    override val canRedoProperty: ReadOnlyBooleanProperty
        get() = controller.canRedoProperty

    override val canSaveProperty: ReadOnlyBooleanProperty
        get() = controller.canSaveProperty

    override val canUndoProperty: ReadOnlyBooleanProperty
        get() = controller.canUndoProperty

    override val selectedToolProperty: ReadOnlyObjectProperty<WorkspaceTool>
        get() = controller.selectedToolProperty

    override val workspaceAnalysisProperty: ReadOnlyObjectProperty<WorkspaceAnalysisData>
        get() = controller.workspaceAnalysisProperty

    private val controller: WorkspaceController by di()

    private lateinit var canvas: Canvas

    private lateinit var lastRenderTimestamp: Instant

    private var contextMenu: ContextMenu? = null
    private lateinit var workspaceRoot: Pane

    //https://www.programmersought.com/article/83891174946/
    override val root = stackpane {
        progressbar {
            visibleWhen(controller.progressProperty)
        }

        pane {
            workspaceRoot = this

            hiddenWhen(controller.progressProperty)

            style {
                borderColor += box(c("black"))
                backgroundColor += c("white")

                cursorProperty().bind(
                    controller.cursorProperty
                )
            }

            styleProperty()

            isScaleShape = false

            canvas(BuildConfig.CANVAS_MAX_WIDTH, BuildConfig.CANVAS_MAX_HEIGHT) {
                //hiddenWhen(controller.progressProperty)
                canvas = this
            }
        }
    }

    private val gc get() = canvas.graphicsContext2D

    private val arguments = requireArguments<NavArguments.Workspace>()

    private lateinit var mousePressedPosition: Point2D

    init {
        setRootListeners()
        setObserver()
        setListeners()
        initAnimationTimer()
        setProjectData()
    }

    /*
     * Common setters
     */
    private fun setProjectData() {
        controller.setProjectData(
            id = arguments.id,
            title = arguments.title,
            file = arguments.file,
            objects = arguments.objects
        )
    }

    private fun setObserver() = with(controller) {
        scaleProperty.onNotNullChange(::setScale)
        errorProperty.onNotNullChange(::showErrorDialog)
        contextMenuStateProperty.onChange(::showContextMenu)
        workspaceProjectDataProperty.onChange(::setProjectData)
        workspaceEditOperationStateProperty.onChange(::onObjectOperationClicked)
    }

    private fun initAnimationTimer() {
        object : AnimationTimer() {
            override fun handle(currentNanoTime: Long) {
                val state = controller.state

                if (!::lastRenderTimestamp.isInitialized || state.timestamp > lastRenderTimestamp) {
                    clearOverlay()
                    state.objects.forEach { it.render(gc) }

                    lastRenderTimestamp = Instant.now()
                }
            }
        }.start()
    }

    private fun setRootListeners() = with(workspaceRoot) {
        setOnMouseClicked {
            onMouseClicked(it)
        }

        setOnMousePressed {
            onMousePressed(it)
        }

        setOnMouseReleased {
            onMouseReleased(it)
        }

        setOnMouseMoved {
            onMoved(it)
        }

        setOnMouseDragged { // Moved & Clicked
            onMouseDragged(it)
        }

        shortcut("ESC") {
            controller.resetCurrentFocus()
        }
    }

    private fun setListeners() {
        canvas.setOnContextMenuRequested {
            controller.onContextMenuRequested(
                clickPoint = point2D(
                    x = it.x,
                    y = it.y
                ),
                screenPoint = point2D(
                    x = it.screenX,
                    y = it.screenY
                )
            )
        }
    }

    private fun onMouseClicked(event: MouseEvent) {
        if (event.button == MouseButton.PRIMARY) {
            controller.onPrimaryMouseClicked(event.toPoint2D())
        }
    }

    private fun onMousePressed(event: MouseEvent) {
        val point = event.toPoint2D()

        mousePressedPosition = point
        if (event.button == MouseButton.MIDDLE) {
            controller.onMiddleMousePressed(point)
        }
    }

    private fun onMouseDragged(event: MouseEvent) {
        onMoved(event)

        when (event.button) {
            MouseButton.PRIMARY -> {
                controller.onPrimaryMouseDragged(event.toPoint2D())
            }
            MouseButton.MIDDLE -> {
                moveWorkspace(event)
            }
        }
    }

    private fun onMouseReleased(event: MouseEvent) {
        val point = event.toPoint2D()

        when (event.button) {
            MouseButton.MIDDLE -> {
                controller.onMiddleMouseReleased(point)
            }
        }
    }

    private fun onMoved(event: MouseEvent) {
        controller.onMoved(event.toPoint2D())
    }

    private fun moveWorkspace(event: MouseEvent) = with(workspaceRoot) {
        if (::mousePressedPosition.isInitialized) {
            translateX = translateX + event.x - mousePressedPosition.x
            translateY = translateY + event.y - mousePressedPosition.y
        }
    }

    private fun setScale(scale: Scale) = with(canvas) {
        scaleX = scale.value
        scaleY = scale.value
        scaleZ = scale.value
    }

    private fun setProjectData(data: WorkspaceProjectData?) {
        if (data != null) {
            title = data.title
        } else {
            showErrorDialog()
        }
    }

    private fun showContextMenu(state: WorkspaceEditObjectState?) {
        contextMenu?.hide()
        contextMenu = null

        if (state != null) {
            val id = state.id
            val point = state.point

            contextMenu = contextmenu {
                item(
                    name = string("workspace.tab.fragment.context_menu.rotate")
                ).action {
                    controller.onObjectOperationClicked(
                        WorkspaceObjectEditOperation.Rotate(
                            id = state.id
                        )
                    )
                }

                item(
                    name = string("workspace.tab.fragment.context_menu.rename")
                ).action {
                    onObjectOperationClicked(
                        WorkspaceEditOperationState.Rename(
                            id = state.id,
                            name = state.name
                        )
                    )
                }

                item(
                    name = string("workspace.tab.fragment.context_menu.change_description")
                ).action {
                    onObjectOperationClicked(
                        WorkspaceEditOperationState.Description(
                            id = state.id,
                            description = state.description
                        )
                    )
                }

                if (state is WorkspaceEditObjectState.Position) {
                    item(
                        name = string("workspace.tab.fragment.context_menu.set_token")
                    ).action {
                        onObjectOperationClicked(
                            WorkspaceEditOperationState.SetToken(
                                id = state.id,
                                tokenNumber = state.tokenNumber
                            )
                        )
                    }
                }

                item(
                    name = string("workspace.tab.fragment.context_menu.delete")
                ).action {
                    controller.onObjectOperationClicked(
                        WorkspaceObjectEditOperation.Delete(id)
                    )
                }
            }.also {
                it.show(
                    canvas,
                    point.x,
                    point.y
                )
            }
        }
    }

    private fun onObjectOperationClicked(state: WorkspaceEditOperationState?) {
        if (state != null) {
            when (state) {
                is WorkspaceEditOperationState.Error -> {
                    showErrorDialog(
                        err = string(state.exception.errorId)
                    )
                }
                is WorkspaceEditOperationState.Rename -> {
                    fieldDialog(
                        title = string("workspace.tab.fragment.dialog.rename"),
                        value = state.name
                    ) {
                        controller.onObjectOperationClicked(
                            WorkspaceObjectEditOperation.Rename(state.id, it)
                        )
                    }
                }
                is WorkspaceEditOperationState.Description -> {
                    fieldDialog(
                        title = string("workspace.tab.fragment.dialog.change_description"),
                        value = state.description
                    ) {
                        controller.onObjectOperationClicked(
                            WorkspaceObjectEditOperation.Description(state.id, it)
                        )
                    }
                }
                is WorkspaceEditOperationState.SetToken -> {
                    fieldDialog(
                        title = string("workspace.tab.fragment.dialog.set_token"),
                        value = state.tokenNumber.toString()
                    ) {
                        val tokenNumber = it.toIntOrNull()

                        if (tokenNumber != null) {
                            controller.onObjectOperationClicked(
                                WorkspaceObjectEditOperation.SetToken(state.id, tokenNumber)
                            )
                        } else {
                            showErrorDialog(
                                string("container.error.invalid_token_number")
                            )
                        }
                    }
                }
            }
        }

    }

    private fun clearOverlay() {
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
    }

    /*
     * Container listeners
     */
    override fun onUndoClicked() {
        controller.onUndoClicked()
    }

    override fun onRedoClicked() {
        controller.onRedoClicked()
    }

    override fun onCleanClicked() {
        controller.onCleanClicked()
    }

    override fun onCloseClicked() {
        owningTab?.close()
    }

    override fun onSaveClicked() {
        controller.saveProject()
    }

    override fun onSaveAsClicked(file: File) {
        controller.saveProjectAs(file)
    }

    override fun onScaleChanged(scale: ScaleOperation) {
        controller.changeScale(scale)
    }

    override fun onMatrixViewClicked() {
        controller.showMatrix()
    }

    override fun onReachabilityTreeClicked() {
        controller.showReachabilityTree()
    }

    override fun onMatrixAnalysisClicked() {
        controller.showMatrixAnalysisSolution()
    }

    override fun setWorkspaceTool(tool: WorkspaceTool) {
        controller.setWorkspaceTool(tool)
    }

    override fun setWorkspaceOrientation(orientation: WorkspaceOrientation) {
        controller.setWorkspaceOrientation(orientation)
    }
}