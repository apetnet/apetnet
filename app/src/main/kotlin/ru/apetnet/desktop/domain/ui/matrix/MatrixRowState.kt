package ru.apetnet.desktop.domain.ui.matrix

import javafx.beans.property.StringProperty
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.exception.IllegalMatrixValueException
import ru.apetnet.ext.ejml.forEachRowIndexed
import tornadofx.property
import tornadofx.stringProperty

data class MatrixRowState(
    private val id: String,
    private val values: List<StringProperty>
) {
    var rowId: String by property(id)
    var rowValues: List<StringProperty> by property(values)

    companion object {
        val DEFAULT_VALUE get() = stringProperty("0")
    }
}

fun List<MatrixRowState>.mapToTypedArray(): Array<DoubleArray> {
    return map { it.toDoubleArray() }.toTypedArray()
}

fun List<MatrixRowState>.mapValue(callback: (value: String) -> String): List<MatrixRowState> {
    return map { row ->
        row.copy(
            values = row.rowValues.map { prop ->
                stringProperty(
                    callback.invoke(prop.value)
                )
            }
        )
    }
}

val List<MatrixRowState>.rowsNum: Int get() = size

val List<MatrixRowState>.colsNum: Int get() = first().rowValues.size

fun MatrixRowState.toDoubleArray(): DoubleArray {
    val array = DoubleArray(rowValues.size)

    rowValues.forEachIndexed { index, prop ->
        val value = prop.value

        try {
            array[index] = value.toDouble()
        } catch (e: NumberFormatException) {
            throw IllegalMatrixValueException(value)
        }

    }
    return array
}

fun SimpleMatrix.toMatrixRowStateList(idCallback: (Int) -> String): List<MatrixRowState> {
    val resultMatrix = mutableListOf<MatrixRowState>()

    forEachRowIndexed { rowId, row ->
        val state = MatrixRowState(
            id = idCallback.invoke(rowId),
            values = row.map { stringProperty("${it.toInt()}") }
        )
        resultMatrix.add(state)
    }

    return resultMatrix
}