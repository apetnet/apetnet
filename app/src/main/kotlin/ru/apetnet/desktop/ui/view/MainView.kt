package ru.apetnet.desktop.ui.view

import javafx.beans.value.ObservableValue
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import javafx.stage.Modality
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.app.Application
import ru.apetnet.desktop.domain.service.importmatrix.ImportMatrixResult
import ru.apetnet.desktop.domain.ui.main.AnalysisDialogInfo
import ru.apetnet.desktop.navigation.NavArguments
import ru.apetnet.desktop.navigation.tab
import ru.apetnet.desktop.domain.ui.main.MainMenuActionItem
import ru.apetnet.desktop.domain.ui.main.MainMenuOrientationItem
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import ru.apetnet.desktop.domain.ui.workspace.WorkspaceOrientation
import ru.apetnet.desktop.domain.ui.main.WorkspaceTool
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.ui.controller.main.MainController
import ru.apetnet.desktop.ui.controller.main.MainController.Companion.WORKSPACE_ORIENTATION
import ru.apetnet.desktop.ui.controller.main.MainController.Companion.WORKSPACE_TOOL
import ru.apetnet.desktop.ui.fragment.WorkspaceFragment
import ru.apetnet.desktop.ui.listeners.*
import ru.apetnet.desktop.util.log.safeLog
import ru.apetnet.desktop.util.resource.string
import ru.apetnet.ext.fx.java.scene.canvas.ScaleOperation
import ru.apetnet.ext.fx.tornado.*
import ru.apetnet.ext.fx.tornado.menuitem
import tornadofx.*
import java.io.File
import java.util.*

class MainView : View(Application.TITLE) {
    companion object {
        const val TAG: String = "MainContainer"
    }

    private val controller: MainController by di()

    private lateinit var tabPane: TabPane
    private val tabs: List<Tab>
        get() = if (::tabPane.isInitialized) {
            tabPane.tabs
        } else {
            listOf()
        }

    private val selectedTab: TabPane?
        get() {
            return if (::tabPane.isInitialized) {
                tabPane
            } else {
                null
            }
        }

    private val selectedWorkspace: OnClickAtContainerListener?
        get() = selectedTab?.contentUiComponent()

    private var unsavedProjectsAmount: Long = 0

    private lateinit var importMatrixView: ImportMatrixView
    private lateinit var exportMatrixView: ExportMatrixView
    private lateinit var reachabilityTreeView: ReachabilityTreeView
    private lateinit var matrixAnalysisView: MatrixAnalysisView

    override val root = borderpane {
        prefWidth = Application.WINDOW_WIDTH
        prefHeight = Application.WINDOW_HEIGHT

        top {
            menubar {
                menu(
                    name = string("container.menu.file")
                ) {
                    menuitem(
                        name = string("container.menu.file.item.new"),
                        keyCombination = "CTRL+N"
                    ) {
                        openNewProject()
                    }

                    menuitem(
                        name = string("container.menu.file.item.open"),
                        keyCombination = "CTRL+O"
                    ) {
                        showOpenProjectDialog()
                    }

                    menuitem(
                        name = string("container.menu.file.item.import_matrix"),
                        keyCombination = "CTRL+I"
                    ) {
                        showImportMatrixDialog()
                    }

                    menuitem(
                        name = string("container.menu.file.item.save"),
                        keyCombination = "CTRL+S",
                        enableWhen = { controller.canSaveProperty }
                    ) {
                        selectedWorkspace?.onSaveClicked()
                    }

                    menuitem(
                        name = string("container.menu.file.item.save_as"),
                        keyCombination = "CTRL+SHIFT+S",
                        enableWhen = { controller.hasTabAtTapPaneProperty }
                    ) {
                        showSaveProjectDialog()
                    }

                    menuitem(
                        name = string("container.menu.file.item.quit"),
                        keyCombination = "CTRL+Q"
                    ) {
                        close()
                    }
                }

                menu(
                    name = string("container.menu.edit")
                ) {
                    workspaceActionItem(
                        name = string("container.menu.edit.item.undo"),
                        keyCombination = "CTRL+Z",
                        enableWhen = { controller.canUndoProperty }
                    ) {
                        selectedWorkspace?.onUndoClicked()
                    }

                    workspaceActionItem(
                        name = string("container.menu.edit.item.redo"),
                        keyCombination = "CTRL+SHIFT+Z",
                        enableWhen = { controller.canRedoProperty }
                    ) {
                        selectedWorkspace?.onRedoClicked()
                    }

                    workspaceActionItem(
                        name = string("container.menu.edit.item.clean")
                    ) {
                        selectedWorkspace?.onCleanClicked()
                    }
                }

                menu(
                    name = string("container.menu.view")
                ) {
                    workspaceActionItem(
                        name = string("container.menu.view.close_tab"),
                        keyCombination = "CTRL+W"
                    ) {
                        selectedWorkspace?.onCloseClicked()
                    }

                    if (BuildConfig.FLAG_SCALE_ENABLED) {
                        workspaceActionMenu(
                            name = string("container.menu.view.scale")
                        ) {
                            menucheckitem(
                                name = string("container.menu.view.scale_in"),
                                keyCombination = "CTRL+=",
                                action = {
                                    selectedWorkspace?.onScaleChanged(
                                        scale = ScaleOperation.SCALE_IN
                                    )
                                }
                            )
                            menucheckitem(
                                name = string("container.menu.view.scale_out"),
                                keyCombination = "CTRL+-",
                                action = {
                                    selectedWorkspace?.onScaleChanged(
                                        scale = ScaleOperation.SCALE_OUT
                                    )
                                }
                            )
                        }
                    }

                    workspaceActionMenu(
                        name = string("container.menu.view.orientation_by_default"),
                    ) {
                        val items = listOf(
                            MainMenuOrientationItem(
                                nameId = "container.menu.view.orientation.horizontal",
                                keyCombination = "CTRL+SHIFT+H",
                                orientation = WorkspaceOrientation.Horizontal
                            ),
                            MainMenuOrientationItem(
                                nameId = "container.menu.view.orientation.vertical",
                                keyCombination = "CTRL+SHIFT+V",
                                orientation = WorkspaceOrientation.Vertical
                            )
                        )

                        items.forEach {
                            menucheckitem(
                                name = string(it.nameId),
                                keyCombination = it.keyCombination,
                                selected = controller.workspaceOrientationProperty.select { orientation ->
                                    booleanProperty(orientation == it.orientation)
                                },
                                action = {
                                    controller.setWorkspaceOrientation(
                                        orientation = it.orientation
                                    )
                                }
                            )
                        }
                    }
                }

                menu(
                    name = string("container.menu.tools")
                ) {
                    val items = listOf(
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.cursor",
                            keyCombination = "CTRL+SHIFT+C",
                            tool = WorkspaceTool.Cursor
                        ),
                        MainMenuActionItem.Separator,
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.add_position",
                            keyCombination = "CTRL+SHIFT+P",
                            tool = WorkspaceTool.AddPosition
                        ),
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.add_transition",
                            keyCombination = "CTRL+SHIFT+T",
                            tool = WorkspaceTool.AddTransition
                        ),
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.add_arc",
                            keyCombination = "CTRL+SHIFT+A",
                            tool = WorkspaceTool.AddArc
                        ),
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.set_token",
                            keyCombination = "CTRL+SHIFT+N",
                            tool = WorkspaceTool.SetToken
                        ),
                        MainMenuActionItem.Separator,
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.move",
                            keyCombination = "CTRL+SHIFT+M",
                            tool = WorkspaceTool.Move
                        ),
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.rename",
                            keyCombination = "CTRL+SHIFT+R",
                            tool = WorkspaceTool.Rename
                        ),
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.rotate",
                            keyCombination = "CTRL+SHIFT+I",
                            tool = WorkspaceTool.Rotate
                        ),
                        MainMenuActionItem.Tool(
                            nameId = "container.menu.tools.delete",
                            keyCombination = "CTRL+SHIFT+D",
                            tool = WorkspaceTool.Delete
                        )
                    )

                    items.forEach {
                        when (it) {
                            is MainMenuActionItem.Tool -> {
                                menucheckitem(
                                    name = string(it.nameId),
                                    keyCombination = it.keyCombination,
                                    enableWhen = {
                                        controller.hasTabAtTapPaneProperty
                                    },
                                    selected = controller.workspaceToolProperty.select { operation ->
                                        booleanProperty(operation == it.tool)
                                    },
                                    action = {
                                        controller.setWorkspaceTool(
                                            tool = it.tool
                                        )
                                    }
                                )
                            }
                            MainMenuActionItem.Separator -> {
                                separator()
                            }
                        }

                    }
                }

                menu(
                    name = string("container.menu.analysis")
                ) {
                    workspaceActionItem(
                        name = string("container.menu.analysis.matrix_view"),
                        keyCombination = "CTRL+M",
                        action = {
                            selectedWorkspace?.onMatrixViewClicked()
                        }
                    )

                    workspaceActionItem(
                        name = string("container.menu.analysis.reachability_tree"),
                        keyCombination = "CTRL+T",
                        action = {
                            selectedWorkspace?.onReachabilityTreeClicked()
                        }
                    )

                    workspaceActionItem(
                        name = string("container.menu.analysis.matrix_analysis"),
                        keyCombination = "CTRL+A",
                        action = {
                            selectedWorkspace?.onMatrixAnalysisClicked()
                        }
                    )
                }
            }
        }

        center {
            tabpane {
                tabPane = this
                tabDragPolicy = TabPane.TabDragPolicy.REORDER

                selectionModel.selectedIndexProperty().onChange(controller::onSelectedIndexChanged)


                selectionModel.selectedItemProperty().onChange { tab ->
                    if (tab != null) {
                        val component = tab.uiContentComponent<UIComponent>()

                        if (component is HasCanvasProperties) {
                            controller.bindWorkspaceMousePosition(component.mousePositionProperty)
                            controller.bindWorkspaceScaleProperty(component.scaleProperty)
                        }

                        if (component is HasSelectedToolProperty) {
                            controller.bindWorkspaceToolProperty(component.selectedToolProperty)
                        }

                        if (component is HasWorkspaceProperties) {
                            controller.bindCanUndoProperty(component.canUndoProperty)
                            controller.bindCanRedoProperty(component.canRedoProperty)
                            controller.bindCanSaveProperty(component.canSaveProperty)
                        }

                        if (component is HasAnalysisProperties) {
                            controller.bindAnalysisProperty(component.workspaceAnalysisProperty)
                        }

                        setTabWorkspaceOperation(
                            tab = tab,
                            tool = controller.workspaceTool
                        )

                        setTabWorkspaceOrientation(
                            tab = tab,
                            orientation = controller.workspaceOrientation
                        )
                    }
                }
            }
        }

        bottom {
            vbox {
                separator()
                toolbar {
                    pane {
                        hgrow = Priority.ALWAYS
                    }

                    label {
                        textProperty().bind(
                            controller.workspaceMousePositionProperty.selectString { point ->
                                string("container.bottom.mouse_position", point.x, point.y)
                            }
                        )
                        visibleWhen { controller.workspaceMousePositionProperty.isNotNull }
                    }

                    if (BuildConfig.FLAG_SCALE_ENABLED) {
                        label {
                            textProperty().bind(
                                controller.workspaceScaleProperty.selectString { scale ->
                                    string("container.bottom.scale", scale.value)
                                }
                            )
                            visibleWhen { controller.workspaceScaleProperty.isNotNull }
                        }
                    }

                    label {
                        textProperty().bind(
                            controller.workspaceToolProperty.selectString { resource ->
                                string("container.bottom.tool", string(resource.toStringResource()))
                            }
                        )
                        visibleWhen { controller.workspaceToolProperty.isNotNull }
                    }

                    label {
                        textProperty().bind(
                            controller.workspaceOrientationProperty.selectString { resource ->
                                string("container.bottom.orientation", string(resource.toStringResource()))
                            }
                        )
                        visibleWhen { controller.workspaceOrientationProperty.isNotNull }
                    }
                }
            }
        }
    }

    init {
        setOnChange()
    }

    private fun setOnChange() = with(controller) {
        workspaceToolProperty.onChange { value ->
            safeLog(TAG) { value.toString() }
            tabs.forEach { tab ->
                setTabWorkspaceOperation(tab, value)
            }
        }

        workspaceOrientationProperty.onChange { value ->
            safeLog(TAG) { value.toString() }
            tabs.forEach { tab ->
                setTabWorkspaceOrientation(tab, value)
            }
        }

        hasTabAtTapPaneProperty.onChange { has ->
            val currentTool = workspaceToolProperty.value

            when {
                (has && currentTool == null) -> {
                    setWorkspaceTool(WORKSPACE_TOOL)
                    setWorkspaceOrientation(WORKSPACE_ORIENTATION)
                }
                !(has && currentTool == null) -> {
                    setWorkspaceTool(null)
                    setWorkspaceOrientation(null)
                }
            }
        }

        showAnalysisDialogProperty.onChange {
            when (it) {
                is AnalysisDialogInfo.ExportMatrix -> {
                    showExportMatrixDialog(it.items)
                }
                is AnalysisDialogInfo.ReachabilityTree -> {
                    showReachabilityTreeDialog(it.items)
                }
                is AnalysisDialogInfo.MatrixAnalysis -> {
                    showMatrixAnalysisDialog(it.items)
                }
            }
        }
    }

    private fun openNewProject(objects: List<WorkspaceObject> = listOf()) {
        openProject(
            title = when {
                unsavedProjectsAmount > 0 -> {
                    string("workspace.tab.project.new_labeled", unsavedProjectsAmount)
                }
                else -> {
                    string("workspace.tab.project.new")
                }
            },
            file = null,
            objects = objects
        )

        unsavedProjectsAmount++
    }

    private fun showOpenProjectDialog() {
        val chooseFile = chooseFile(
            title = string("dialog.project.open"),
            mode = FileChooserMode.Single,
            filters = arrayOf(
                extensionFilter("JSON files (*.json)", "*.json")
            )
        ).firstOrNull()

        if (chooseFile != null) {
            val data = controller.loadProject(chooseFile)
            val file = data.file

            openProject(
                id = data.id,
                title = file.name,
                file = file,
                objects = data.items
            )
        }
    }

    private fun importMatrix(data: ImportMatrixResult) {
        openNewProject(
            objects = controller.importProject(data).items
        )
    }

    private fun showSaveProjectDialog() {
        val file = chooseFile(
            title = string("dialog.project.save_as"),
            mode = FileChooserMode.Save,
            filters = arrayOf(
                extensionFilter("JSON files (*.json)", "*.json")
            )
        ).firstOrNull()

        if (file != null) {
            selectedWorkspace?.onSaveAsClicked(file)
        }
    }

    private fun openProject(
        id: UUID = UUID.randomUUID(),
        title: String,
        file: File?,
        objects: List<WorkspaceObject> = listOf()
    ) {
        val args = NavArguments.Workspace(
            id = id,
            title = title,
            file = file,
            objects = objects
        )

        tabPane.tab<WorkspaceFragment>(args)
            .select()
    }

    private fun setTabWorkspaceOperation(tab: Tab, tool: WorkspaceTool?) {
        val component = tab.uiContentComponent<HasWorkspaceTool>()

        component?.setWorkspaceTool(
            tool = tool ?: WORKSPACE_TOOL
        )
    }

    private fun setTabWorkspaceOrientation(tab: Tab, orientation: WorkspaceOrientation?) {
        val component = tab.uiContentComponent<HasWorkspaceOrientation>()

        component?.setWorkspaceOrientation(
            orientation = orientation ?: WORKSPACE_ORIENTATION
        )
    }

    private fun showImportMatrixDialog() {
        safeLog(TAG) { "showImportMatrixDialog" }
        if (::importMatrixView.isInitialized) {
            importMatrixView.close()
        }

        importMatrixView = find()
        importMatrixView.setOnClickListener(::importMatrix)
        importMatrixView.openModal(
            resizable = true,
            modality = Modality.NONE,
            owner = null,
            block = false
        )
    }

    private fun showExportMatrixDialog(items: List<WorkspaceObjectBrief>) {
        safeLog(TAG) { "showExportMatrixDialog: $items" }

        if (::exportMatrixView.isInitialized) {
            exportMatrixView.close()
        }

        exportMatrixView = find(
            ExportMatrixView::parameters to NavArguments.ShowMatrix(items)
        )

        exportMatrixView.openModal(
            resizable = true,
            modality = Modality.NONE,
            owner = null,
            block = false
        )
    }

    private fun showReachabilityTreeDialog(items: List<WorkspaceObjectBrief>) {
        safeLog(TAG) { "showReachabilityTreeDialog: $items" }

        if (::reachabilityTreeView.isInitialized) {
            reachabilityTreeView.close()
        }

        reachabilityTreeView = find(
            ReachabilityTreeView::parameters to NavArguments.ReachabilityTree(items)
        )

        reachabilityTreeView.openModal(
            resizable = true,
            modality = Modality.NONE,
            owner = null,
            block = false
        )
    }

    private fun showMatrixAnalysisDialog(items: List<WorkspaceObjectBrief>) {
        safeLog(TAG) { "showMatrixAnalysisDialog: $items" }

        if (::matrixAnalysisView.isInitialized) {
            matrixAnalysisView.close()
        }

        matrixAnalysisView = find(
            MatrixAnalysisView::parameters to NavArguments.MatrixAnalysis(items)
        )

        matrixAnalysisView.openModal(
            resizable = true,
            modality = Modality.NONE,
            owner = null,
            block = false
        )
    }

    private fun Menu.workspaceActionItem(
        name: String,
        keyCombination: String? = null,
        enableWhen: (() -> ObservableValue<Boolean>)? = null,
        action: () -> Unit = {}
    ): MenuItem {
        return menuitem(
            name = name,
            keyCombination = keyCombination,
            enableWhen = enableWhen ?: {
                controller.hasTabAtTapPaneProperty
            },
            action = {
                action.invoke()
            }
        )
    }

    private fun Menu.workspaceActionMenu(
        name: String,
        op: Menu.() -> Unit = {}
    ): MenuItem {
        return menu(name) {
            enableWhen { controller.hasTabAtTapPaneProperty }
            op.invoke(this)
        }
    }


}