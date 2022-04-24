package ru.apetnet.ext.fx.tornado

import javafx.scene.Node
import javafx.scene.control.Tab
import javafx.scene.control.TableView
import javafx.scene.layout.GridPane
import tornadofx.gridpaneConstraints
import tornadofx.uiComponent

inline fun <reified T : Any> Tab.uiContentComponent(): T? = content.uiComponent()

fun GridPane.reset() {
    children.clear()
    columnConstraints.clear()
    rowConstraints.clear()
}

inline fun <T : Node> T.setGridPaneConstraints(
    rowIndex: Int = 0,
    colIndex: Int = 0
): T {
    /*
    Нужно будет, чтобы скрыть названия столбцов, когда буду реализовывать возможность их переименования
    stylesheet {
        Stylesheet.columnHeaderBackground {
            maxHeight = 0.px
            prefHeight = 0.px
            minHeight = 0.px
        }
    }

     */
    return gridpaneConstraints {
        columnSpan = 1
        columnRowIndex(
            columnIndex = colIndex,
            rowIndex = rowIndex
        )
    }
}