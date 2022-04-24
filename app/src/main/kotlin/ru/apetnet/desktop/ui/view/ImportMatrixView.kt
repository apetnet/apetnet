package ru.apetnet.desktop.ui.view

import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import ru.apetnet.desktop.domain.service.importmatrix.ImportMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.KindIncidenceMatrices
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.importmatrix.*
import ru.apetnet.desktop.ui.controller.matrix.importmatrix.ImportMatrixController
import ru.apetnet.desktop.ui.controller.matrix.importmatrix.ImportMatrixController.Companion.IMPORT_MATRIX_SOURCE
import ru.apetnet.desktop.ui.parent.ParentMatrixView
import ru.apetnet.ext.fx.tornado.onNotNullChange
import ru.apetnet.ext.fx.tornado.reset
import ru.apetnet.ext.fx.tornado.string
import tornadofx.*

class ImportMatrixView : ParentMatrixView() {
    companion object {
        const val TAG: String = "ImportMatrixView"
        const val PADDING: Double = 5.0
    }

    private val controller: ImportMatrixController by di()

    private lateinit var tableViewI: TableView<MatrixRowState>
    private lateinit var tableViewO: TableView<MatrixRowState>
    private lateinit var tableViewC: TableView<MatrixRowState>
    private lateinit var tableViewMarking: TableView<MatrixRowState>
    private lateinit var tableGridPane: GridPane

    private lateinit var listener: (ImportMatrixResult) -> Unit

    override val root = stackpane {
        val orientationList = listOf(
            ImportMatrixOrientation.Vertical(
                string("dialog.import_matrix.orientation_vertical")
            ),
            ImportMatrixOrientation.Horizontal(
                string("dialog.import_matrix.orientation_horizontal")
            )
        )
        controller.setOrientation(orientationList.first())

        progressbar {
            visibleWhen(controller.progressProperty)
        }
        vbox {
            visibleWhen(!controller.progressProperty)

            gridpane {
                padding = insets(PADDING)

                hgap = 10.0
                vgap = 10.0

                alignment = Pos.BASELINE_CENTER

                label(
                    text = string("dialog.import_matrix.p_num")
                ) {
                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 0
                    )
                }

                spinner(
                    property = controller.positionNumberProperty,
                    editable = true
                ) {
                    styleClass.clear()
                    setGridPaneConstraints(
                        colIndex = 1,
                        rowIndex = 0
                    )
                }

                label(
                    text = string("dialog.import_matrix.t_num")
                ) {
                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 1
                    )
                }

                spinner(
                    property = controller.transitionNumberProperty,
                    editable = true
                ) {
                    styleClass.clear()
                    setGridPaneConstraints(
                        colIndex = 1,
                        rowIndex = 1
                    )
                }

                label(
                    text = string("dialog.import_matrix.d_num")
                ) {
                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 2
                    )
                }

                combobox(
                    property = controller.kindIncidenceMatricesProperty,
                    values = KindIncidenceMatrices.values().toList()
                ) {
                    setGridPaneConstraints(
                        colIndex = 1,
                        rowIndex = 2
                    )
                }

                label(
                    text = string("dialog.import_matrix.orientation")
                ) {
                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 3
                    )
                }

                combobox(
                    property = controller.importMatrixOrientationProperty,
                    values = orientationList
                ) {
                    setGridPaneConstraints(
                        colIndex = 1,
                        rowIndex = 3
                    )
                }
            }

            stackpane {
                padding = insets(PADDING)
                vgrow = Priority.ALWAYS

                gridpane {
                    tableGridPane = this
                    refreshTables(IMPORT_MATRIX_SOURCE)
                }
            }

            hbox(PADDING) {
                padding = insets(bottom = PADDING)
                alignment = Pos.CENTER

                button(
                    text = string("dialog.import_matrix.ok_button")
                ) {
                    enableWhen(controller.hasImportDataProperty)

                    action {
                        controller.onImportClicked()
                    }
                }

                button(
                    text = string("dialog.import_matrix.reset_button")
                ) {
                    enableWhen(controller.hasImportDataProperty)

                    action {
                        controller.reset()
                    }
                }
            }
        }
    }

    init {
        title = string("dialog.import_matrix.title")
        setObserver()
    }

    override fun onUndock() {
        controller.reset()
        super.onUndock()
    }

    private fun setObserver() = with(controller) {
        errorProperty.onNotNullChange(::showErrorDialog)
        positionNumberProperty.onNotNullChange(controller::onPositionNumberChanged)
        transitionNumberProperty.onNotNullChange(controller::onTransitionNumberChanged)
        kindIncidenceMatricesProperty.onNotNullChange {
            refreshTables(it)
            onImportMatrixSourceChanged(it)
        }
        stateProperty.onChange(::setMatrixState)
        matrixResultProperty.onNotNullChange(::passImportMatrixResult)
    }

    fun setOnClickListener(callback: (ImportMatrixResult) -> Unit) {
        listener = callback
    }

    private fun refreshTables(source: KindIncidenceMatrices) = with(tableGridPane) {
        reset()

        val nextIndex: Int
        when (source) {
            KindIncidenceMatrices.IO -> {
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
                nextIndex = 2
            }
            KindIncidenceMatrices.C -> {
                tableview(
                    items = controller.tableMatC
                ) { // D Matrix
                    tableViewC = this
                    setGridPaneConstraints(
                        colIndex = 0,
                        rowIndex = 0
                    )
                }
                nextIndex = 1
            }
        }

        tableview(
            items = controller.tableMarking
        ) { // Marking Matrix
            tableViewMarking = this
            setGridPaneConstraints(
                colIndex = nextIndex,
                rowIndex = 0
            )
        }

        setHGridPaneProperties(
            colNumber = nextIndex + 1,
            rowId = 0
        )
    }

    private fun setMatrixState(state: ImportMatrixState?) {
        if (state != null) {
            val colNumMatrix = state.colNumMatrix

            when (state.source) {
                KindIncidenceMatrices.IO -> {
                    refreshTableContent(
                        type = ImportMatrixType.I,
                        colNum = colNumMatrix,
                        isValid = state.isValid
                    )

                    refreshTableContent(
                        type = ImportMatrixType.O,
                        colNum = colNumMatrix,
                        isValid = state.isValid
                    )
                }
                KindIncidenceMatrices.C -> {
                    refreshTableContent(
                        type = ImportMatrixType.C,
                        colNum = colNumMatrix,
                        isValid = state.isValid
                    )
                }
            }

            refreshTableContent(
                type = ImportMatrixType.Marking,
                colNum = state.colNumMarking,
                isValid = state.isValid
            )
        } else {
            showErrorDialog()
        }
    }

    private fun passImportMatrixResult(result: ImportMatrixResult) {
        if (::listener.isInitialized) {
            listener.invoke(result)
            currentStage?.close() // modalStage
        }
    }

    private fun refreshTableContent(
        type: ImportMatrixType,
        colNum: Int,
        isValid: Boolean
    ) {
        fillTable(
            type = type,
            tableView = when (type) {
                ImportMatrixType.I -> tableViewI
                ImportMatrixType.O -> tableViewO
                ImportMatrixType.C -> tableViewC
                ImportMatrixType.Marking -> tableViewMarking
            },
            colNum = colNum,
            isValid = isValid
        )
    }

    private fun fillTable(
        type: ImportMatrixType,
        tableView: TableView<MatrixRowState>,
        colNum: Int,
        isValid: Boolean
    ) = with(tableView) {
        columns.clear()

        if (isValid) {
            val (rowPrefix, colPrefix) = when (type) {
                ImportMatrixType.I,
                ImportMatrixType.O,
                ImportMatrixType.C -> "P" to "T"
                ImportMatrixType.Marking -> "" to "P"
            }

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
                        "$rowPrefix${it.value.rowId}"
                    }
                }
            }

            repeat(colNum) { id ->
                val colName = "$colPrefix${id + 1}"

                column(
                    title = colName,
                    cellType = String::class
                ) {
                    cellFactory = TextFieldTableCell.forTableColumn()
                    isSortable = false

                    value {
                        it.value.rowValues[id]
                    }
                }.makeEditable()
            }
        }
    }
}