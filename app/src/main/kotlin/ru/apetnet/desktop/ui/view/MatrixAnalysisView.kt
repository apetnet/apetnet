package ru.apetnet.desktop.ui.view

import javafx.scene.control.TableView
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.Priority
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.matrixanalysis.MatrixAnalysisProperty
import ru.apetnet.desktop.domain.ui.matrix.matrixanalysis.MatrixAnalysisPropertyItem
import ru.apetnet.desktop.domain.ui.matrix.matrixanalysis.MatrixAnalysisState
import ru.apetnet.desktop.navigation.NavArguments
import ru.apetnet.desktop.ui.controller.matrix.matrixanalysis.MatrixAnalysisController
import ru.apetnet.desktop.ui.parent.ParentMatrixView
import ru.apetnet.ext.fx.tornado.onNotNullChange
import ru.apetnet.ext.fx.tornado.string
import tornadofx.*

class MatrixAnalysisView : ParentMatrixView() {
    companion object {
        const val TAG: String = "MatrixAnalysisView"
    }

    private val controller: MatrixAnalysisController by di()

    private lateinit var tableViewSolX: TableView<MatrixRowState>
    private lateinit var tableViewSolY: TableView<MatrixRowState>
    private lateinit var tableViewR: TableView<MatrixRowState>

    val parameters: NavArguments.MatrixAnalysis by param()

    override val root = stackpane {
        progressbar {
            visibleWhen(controller.progressProperty)
        }
        vbox {
            hiddenWhen(controller.progressProperty)

            gridpane {
                hgap = PADDING
                vgap = PADDING
                vgrow = Priority.ALWAYS
                hgrow = Priority.ALWAYS

                setVGridPaneProperties(
                    rowNumber = 3
                )

                tableview(
                    items = controller.tableSolX
                ) { // X solutions
                    tableViewSolX = this

                    prefHeight = MAX_ONE_ROW_TABLE_HEIGHT

                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 0
                    )
                }

                tableview(
                    items = controller.tableSolY
                ) { // Y solutions
                    tableViewSolY = this

                    prefHeight = MAX_ONE_ROW_TABLE_HEIGHT

                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 1
                    )
                }

                tableview(
                    items = controller.tableResult
                ) { // Y solutions
                    tableViewR = this

                    prefHeight = MAX_ONE_ROW_TABLE_HEIGHT

                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 2
                    )
                }
            }
        }
    }

    init {
        title = string("dialog.matrix_analysis.title")
        setObserver()
    }

    private fun setObserver() = with(controller) {
        errorProperty.onNotNullChange(::showErrorDialog)
        stateProperty.onChange(::setMatrixAnalysisState)
    }

    override fun onDock() {
        controller.refreshTable(parameters.items)
        super.onDock()
    }

    override fun onUndock() {
        controller.reset()
        super.onUndock()
    }

    private fun setMatrixAnalysisState(state: MatrixAnalysisState?) {
        if (state != null) {
            fillTable(
                tableView = tableViewSolX,
                colNames = state.colNamesX
            )

            fillTable(
                tableView = tableViewSolY,
                colNames = state.colNamesY
            )

            fillTable(
                tableView = tableViewR,
                colNames = listOf(
                    string("container.menu.analysis.matrix_analysis.property_name"),
                    string("container.menu.analysis.matrix_analysis.property_value"),
                )
            )

            fillResult(
                state.properties
            )
        } else {
            showErrorDialog()
        }
    }

    private fun fillResult(
        properties: List<MatrixAnalysisProperty>
    ) {
        controller.setResult(
            properties = properties.map { property ->
                MatrixAnalysisPropertyItem(
                    name = string(
                        when (property) {
                            is MatrixAnalysisProperty.Conservative -> "container.menu.analysis.matrix_analysis.conservative"
                            is MatrixAnalysisProperty.Consistent -> "container.menu.analysis.matrix_analysis.consistent"
                        }
                    ),
                    value = string(
                        if (property.flag) {
                            "container.menu.analysis.matrix_analysis.property_status_yes"
                        } else {
                            "container.menu.analysis.matrix_analysis.property_status_no"
                        }
                    )
                )
            }
        )
    }

    private fun fillTable(
        tableView: TableView<MatrixRowState>,
        colNames: List<String>
    ) = with(tableView) {
        columns.clear()

        repeat(colNames.size) { id ->
            column(
                title = colNames[id],
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
}