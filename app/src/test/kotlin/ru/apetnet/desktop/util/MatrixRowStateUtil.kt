package ru.apetnet.desktop.util

import javafx.beans.property.StringProperty
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.colsNum
import ru.apetnet.desktop.domain.ui.matrix.rowsNum
import ru.apetnet.ext.ejml.numCols
import ru.apetnet.ext.ejml.numRows
import tornadofx.stringProperty
import kotlin.math.min
import kotlin.random.Random
import kotlin.random.nextInt

internal object MatrixRowStateUtil {
    fun getRandomList(
        rowsNum: Int = RndUtil.inRange(6, 12),
        colsNum: Int = rowsNum / 2,
        range: IntRange = IntRange(-1, 1),
    ): List<MatrixRowState> {
        val list = mutableListOf<MatrixRowState>()

        repeat(rowsNum) {
            val items = mutableListOf<StringProperty>()

            repeat(colsNum) {
                val value = Random.nextInt(range)

                items.add(
                    stringProperty("$value")
                )
            }

            list.add(
                MatrixRowState(
                    id = (it + BuildConfig.MATRIX_ROW_OFFSET).toString(),
                    values = items
                )
            )
        }

        return list
    }

    fun hasEntry(
        firstList: List<MatrixRowState>,
        secondList: List<MatrixRowState>
    ): Boolean {
        val rowsNum = min(firstList.rowsNum, secondList.rowsNum)
        val colsNum = min(firstList.colsNum, secondList.colsNum)

        repeat(rowsNum) { rowId ->
            val originalRowState = firstList[rowId]
            val enlargedRowState = secondList[rowId]

            assert(originalRowState.rowId == enlargedRowState.rowId) {
                println("${originalRowState.rowId} == ${enlargedRowState.rowId}")
            }

            repeat(colsNum) { colId ->
                if (originalRowState.rowValues[colId] != enlargedRowState.rowValues[colId]) {
                    return false
                }
            }
        }

        return true
    }

    fun isEqual(
        matrix: SimpleMatrix,
        state: List<MatrixRowState>
    ): Boolean {
        val rowsNum = matrix.numRows
        val colsNum = matrix.numCols

        if (rowsNum != state.rowsNum || colsNum != state.colsNum) {
            return false
        }

        repeat(rowsNum) { rowId ->
            repeat(colsNum) { colId ->
                val matrixValue = matrix.get(rowId, colId)
                val rowStateValue = state[rowId].rowValues[colId]

                if (matrixValue != rowStateValue.value.toDouble()) {
                    return false
                }
            }
        }
        return true
    }
}