package ru.apetnet.desktop.ui.parent

import javafx.scene.Node
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*

abstract class ParentMatrixView : ParentView() {
    companion object {
        const val PADDING: Double = 5.0
        const val DOUBLE_PADDING: Double = 10.0
        const val MAX_ONE_ROW_TABLE_HEIGHT = 100.0
    }

    protected fun GridPane.setHGridPaneProperties(
        colNumber: Int,
        rowId: Int,
        withIndent: Boolean = true
    ): GridPane = apply {
        if (withIndent) {
            applyIndent()
        }

        constraintsForRow(rowId).percentHeight = 100.0 / (rowId + 1)

        repeat(colNumber) { colId ->
            constraintsForColumn(colId).percentWidth = 100.0 / colNumber
        }
    }

    protected fun GridPane.setVGridPaneProperties(
        rowNumber: Int
    ): GridPane = apply {
        applyIndent()

        repeat(rowNumber) { rowId ->
            constraintsForRow(rowId).percentHeight = 100.0 / rowNumber
        }

        constraintsForColumn(0).percentWidth = 100.0
    }

    protected fun VBox.setVBoxProperties(): VBox = apply {
        padding = insets(PADDING)
        vgrow = Priority.ALWAYS
    }

    protected fun <T : Node> T.setGridPaneConstraints(
        colIndex: Int,
        rowIndex: Int
    ): T {
        return gridpaneConstraints {
            columnSpan = 1

            columnRowIndex(
                columnIndex = colIndex,
                rowIndex = rowIndex
            )
        }
    }

    private fun GridPane.applyIndent() {
        padding = insets(PADDING)
        hgap = PADDING
        vgap = PADDING

        vgrow = Priority.ALWAYS
    }
}