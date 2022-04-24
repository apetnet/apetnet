package ru.apetnet.desktop.ui.view

import javafx.scene.control.TableView
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.Priority
import ru.apetnet.desktop.domain.service.reachabilitytree.ReachabilityTreeResult.TransitionInfo
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.reachabilitytree.ReachabilityTreeState
import ru.apetnet.desktop.domain.ui.reachabilitytree.ReachabilityTreeTableType
import ru.apetnet.desktop.navigation.NavArguments
import ru.apetnet.desktop.ui.controller.reachabilitytree.ReachabilityTreeController
import ru.apetnet.desktop.ui.parent.ParentMatrixView
import ru.apetnet.desktop.util.resource.string
import ru.apetnet.ext.fx.tornado.onNotNullChange
import tornadofx.*

class ReachabilityTreeView : ParentMatrixView() {
    companion object {
        const val TAG: String = "ReachabilityTreeView"
    }

    private val controller: ReachabilityTreeController by di()

    val parameters: NavArguments.ReachabilityTree by param()

    private lateinit var tableViewConnections: TableView<MatrixRowState>
    private lateinit var tableViewMarkings: TableView<MatrixRowState>

    override val root = stackpane {
        progressbar {
            visibleWhen(controller.progressProperty)
        }
        stackpane {
            padding = insets(PADDING)
            hiddenWhen(controller.progressProperty)

            vgrow = Priority.ALWAYS

            vbox(PADDING) {
                setVBoxProperties()

                gridpane {
                    hgap = PADDING
                    vgap = PADDING
                    vgrow = Priority.ALWAYS

                    setHGridPaneProperties(
                        colNumber = 2,
                        rowId = 0,
                        withIndent = false
                    )

                    tableview(
                        items = controller.tableConnections
                    ) {
                        tableViewConnections = this

                        setGridPaneConstraints(
                            colIndex = 0,
                            rowIndex = 0
                        )
                    }

                    tableview(
                        items = controller.tableMarkings
                    ) {
                        tableViewMarkings = this

                        setGridPaneConstraints(
                            colIndex = 1,
                            rowIndex = 0
                        )
                    }
                }
            }
        }
    }

    init {
        title = string("dialog.reachability_tree.title")
        setObserver()
    }

    private fun setObserver() = with(controller) {
        stateProperty.onChange(::setMatrixState)
        errorProperty.onNotNullChange(::showErrorDialog)
    }

    private fun setMatrixState(state: ReachabilityTreeState?) {
        if (state != null) {
            fillConnectionsTable(
                columnNames = state.colConnectionNames
            )
            fillMarkingTable(
                columnNames = state.colMarkingNames
            )
        } else {
            showErrorDialog()
        }
    }

    private fun fillMarkingTable(
        columnNames: List<String>
    ) = with(tableViewMarkings) {
        columns.clear()

        fillTable(
            type = ReachabilityTreeTableType.MARKING,
            tableView = this,
            columnNames = columnNames
        )
    }

    private fun fillConnectionsTable(
        columnNames: List<String>
    ) = with(tableViewConnections) {
        columns.clear()

        fillTable(
            type = ReachabilityTreeTableType.CONNECTION,
            tableView = this,
            columnNames = columnNames
        )
    }

    private fun fillTable(
        type: ReachabilityTreeTableType,
        tableView: TableView<MatrixRowState>,
        columnNames: List<String>
    ) = with(tableView) {
        columns.clear()

        column(
            title = "N",
            prop = MatrixRowState::rowId
        ) {
            isSortable = false

            value {
                it.value.rowId
            }
        }

        repeat(columnNames.size) { id ->
            val columnValue = columnNames[id]

            column(
                title = when (type) {
                    ReachabilityTreeTableType.CONNECTION -> getConnectionColumnTitle(columnValue)
                    ReachabilityTreeTableType.MARKING -> getMarkingColumnTitle(columnValue)
                },
                cellType = String::class
            ) {
                cellFactory = TextFieldTableCell.forTableColumn()
                isSortable = false

                value {
                    it.value.rowValues[id]
                }
            }
        }
    }

    override fun onDock() {
        super.onDock()
        controller.refreshTree(parameters.items)
    }

    override fun onUndock() {
        super.onUndock()
        controller.reset()
    }

    private fun getMarkingColumnTitle(value: String): String {
        return value
    }

    private fun getConnectionColumnTitle(value: String): String {
        val column = TransitionInfo.Column.valueOf(value)

        return string(
            when (column) {
                TransitionInfo.Column.SOURCE -> "dialog.reachability_tree.source"
                TransitionInfo.Column.RECEIVER -> "dialog.reachability_tree.receiver"
                TransitionInfo.Column.TRANSITION -> "dialog.reachability_tree.transition"
            }
        )
    }
}