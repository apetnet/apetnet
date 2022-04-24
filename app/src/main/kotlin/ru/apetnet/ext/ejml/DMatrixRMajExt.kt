package ru.apetnet.ext.ejml

import org.ejml.data.DMatrixRMaj

val DMatrixRMaj.lastRow get() = numRows - 1
val DMatrixRMaj.lastCol get() = numCols - 1

fun DMatrixRMaj.getRow(rowId: Int): DoubleArray {
    val arr = DoubleArray(numCols)

    repeat(numCols) { colId ->
        arr[colId] = get(rowId, colId)
    }

    return arr
}