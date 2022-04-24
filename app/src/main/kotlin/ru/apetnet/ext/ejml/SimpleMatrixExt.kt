package ru.apetnet.ext.ejml

import org.ejml.simple.SimpleMatrix
import kotlin.random.Random
import kotlin.random.nextInt

val SimpleMatrix.lastRow get() = numRows() - 1
val SimpleMatrix.lastCol get() = numCols() - 1

val SimpleMatrix.numRows get() = numRows()
val SimpleMatrix.numCols get() = numCols()

fun SimpleMatrix.forEachIndexed(callback: (rowId: Int, colId: Int, value: Double) -> Unit) {
    repeat(numRows) { rowId ->
        repeat(numCols) { colId ->
            callback.invoke(
                rowId,
                colId,
                get(rowId, colId)
            )
        }
    }
}

fun SimpleMatrix.forEach(callback: (value: Double) -> Unit) {
    repeat(numRows) { rowId ->
        repeat(numCols) { colId ->
            callback.invoke(
                get(rowId, colId)
            )
        }
    }
}

fun SimpleMatrix.forEachRow(callback: (row: DoubleArray) -> Unit) {
    forEachRowIndexed { _, value ->
        callback.invoke(value)
    }
}

fun SimpleMatrix.forEachRowIndexed(callback: (rowId: Int, row: DoubleArray) -> Unit) {
    repeat(numRows) { rowId ->
        val row = DoubleArray(numCols)
        repeat(numCols) { colId ->
            row[colId] = get(rowId, colId)
        }
        callback.invoke(rowId, row)
    }
}

fun SimpleMatrix.mapValue(transform: (Double) -> Double): SimpleMatrix {
    val result = SimpleMatrix(numRows, numCols)

    repeat(numRows) { rowId ->
        repeat(numCols) { colId ->
            val value = get(rowId, colId)
            val transformedValue = transform.invoke(value)

            result.set(
                rowId,
                colId,
                transformedValue
            )
        }
    }

    return result
}

fun SimpleMatrix.getRow(rowId: Int): DoubleArray {
    val arr = DoubleArray(numCols)

    repeat(numCols) { colId ->
        arr[colId] = get(rowId, colId)
    }

    return arr
}

fun SimpleMatrix.getColumn(colId: Int): DoubleArray {
    val arr = DoubleArray(numRows)

    repeat(numRows) { rowId ->
        arr[rowId] = get(rowId, colId)
    }

    return arr
}

fun SimpleMatrix.contains(rowId: Int, element: Double): Boolean {
    val arr = getRow(rowId)

    arr.forEach {
        if (it == element) {
            return true
        }
    }

    return false
}

fun SimpleMatrix.fillRandom(start: Int = -1, end: Int = 1) {
    repeat(numRows) { rowId ->
        repeat(numCols) { colId ->
            set(
                rowId,
                colId,
                Random.nextInt(IntRange(start, end)).toDouble()
            )
        }
    }
}