package ru.apetnet.desktop.ui.view

import javafx.scene.control.TableView
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.Priority
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.exportmatrix.ExportMatrixState
import ru.apetnet.desktop.domain.ui.matrix.importmatrix.ImportMatrixType
import ru.apetnet.desktop.navigation.NavArguments
import ru.apetnet.desktop.ui.controller.matrix.exportmatrix.ExportMatrixController
import ru.apetnet.desktop.ui.parent.ParentMatrixView
import ru.apetnet.ext.fx.tornado.onNotNullChange
import ru.apetnet.ext.fx.tornado.string
import tornadofx.*

class ExportMatrixView : ParentMatrixView() {
    companion object {
        const val TAG: String = "ShowMatrixView"
    }

    private val controller: ExportMatrixController by di()

    private lateinit var tableViewI: TableView<MatrixRowState>
    private lateinit var tableViewO: TableView<MatrixRowState>
    private lateinit var tableViewC: TableView<MatrixRowState>
    private lateinit var tableViewMarking: TableView<MatrixRowState>

    val parameters: NavArguments.ShowMatrix by param()

    override val root = stackpane {
        progressbar {
            visibleWhen(controller.progressProperty)
        }
        vbox {
            hiddenWhen(controller.progressProperty)

            vbox(PADDING) {
                setVBoxProperties()

                gridpane {
                    hgap = PADDING
                    vgap = PADDING
                    vgrow = Priority.ALWAYS

                    setHGridPaneProperties(
                        colNumber = 3,
                        rowId = 0,
                        withIndent = false
                    )

                    tableview(
                        items = controller.tableMatI
                    ) { // I Matrix
                        tableViewI = this

                        setGridPaneConstraints(
                            colIndex = 0,
                            rowIndex = 0
                        )
                    }

                    tableview(
                        items = controller.tableMatO
                    ) { // O Matrix
                        tableViewO = this

                        setGridPaneConstraints(
                            colIndex = 1,
                            rowIndex = 0
                        )
                    }

                    tableview(
                        items = controller.tableMatC
                    ) { // C Matrix
                        tableViewC = this

                        setGridPaneConstraints(
                            colIndex = 2,
                            rowIndex = 0
                        )
                    }
                }

                tableview(
                    items = controller.tableMarking
                ) { // Marking Matrix
                    tableViewMarking = this
                    maxHeight = MAX_ONE_ROW_TABLE_HEIGHT

                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 1
                    )
                }
            }
        }
    }

    init {
        title = string("dialog.export_matrix.title")
        setObserver()
    }

    override fun onDock() {
        super.onDock()
        controller.refreshMatrix(parameters.items)
    }

    override fun onUndock() {
        controller.reset()
        super.onUndock()
    }

    private fun setObserver() = with(controller) {
        stateProperty.onChange(::setMatrixState)
        errorProperty.onNotNullChange(::showErrorDialog)
    }

    private fun setMatrixState(state: ExportMatrixState?) {
        if (state != null) {
            val columnNames = state.colMatrixNames

            refreshTableContent(
                type = ImportMatrixType.I,
                columnNames = columnNames
            )

            refreshTableContent(
                type = ImportMatrixType.O,
                columnNames = columnNames
            )

            refreshTableContent(
                type = ImportMatrixType.C,
                columnNames = columnNames
            )

            refreshTableContent(
                type = ImportMatrixType.Marking,
                columnNames = state.colMarkingNames
            )
        } else {
            showErrorDialog()
        }
    }

    private fun refreshTableContent(
        type: ImportMatrixType,
        columnNames: List<String>
    ) {
        fillTable(
            type = type,
            tableView = when (type) {
                ImportMatrixType.I -> tableViewI
                ImportMatrixType.O -> tableViewO
                ImportMatrixType.C -> tableViewC
                ImportMatrixType.Marking -> tableViewMarking
            },
            columnNames = columnNames
        )
    }

    private fun fillTable(
        type: ImportMatrixType,
        tableView: TableView<MatrixRowState>,
        columnNames: List<String>
    ) = with(tableView) {
        columns.clear()

        val typesWithColumn = listOf(
            ImportMatrixType.I,
            ImportMatrixType.O,
            ImportMatrixType.C
        )

        if (type in typesWithColumn) {
            column(
                title = type.name,
                prop = MatrixRowState::rowId
            ) {
                isSortable = false

                value {
                    it.value.rowId
                }
            }
        }

        repeat(columnNames.size) { id ->
            column(
                title = columnNames[id],
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
