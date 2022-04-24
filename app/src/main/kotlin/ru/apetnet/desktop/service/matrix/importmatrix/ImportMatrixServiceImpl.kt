package ru.apetnet.desktop.service.matrix.importmatrix

import com.google.common.math.DoubleMath
import javafx.beans.property.StringProperty
import javafx.geometry.Orientation
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.service.importmatrix.ImportMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.mapToTypedArray
import ru.apetnet.desktop.exception.IllegalMatrixValueException
import ru.apetnet.ext.ejml.forEach
import ru.apetnet.ext.ejml.mapValue
import ru.apetnet.ext.ejml.numCols
import ru.apetnet.ext.ejml.numRows

class ImportMatrixServiceImpl : ImportMatrixService {
    override fun collectMatrix(list: List<MatrixRowState>, rowsNum: Int, colsNum: Int): List<MatrixRowState> {
        val newMatrix = mutableListOf<MatrixRowState>()

        repeat(rowsNum) { rowId ->
            val oldValues = list.getOrNull(rowId)?.rowValues
            val newValues = mutableListOf<StringProperty>()

            repeat(colsNum) { colId ->
                val newValue = oldValues?.getOrNull(colId) ?: MatrixRowState.DEFAULT_VALUE
                newValues.add(newValue)
            }

            newMatrix.add(
                MatrixRowState(
                    id = "${rowId + BuildConfig.MATRIX_ROW_OFFSET}",
                    values = newValues
                )
            )
        }

        return newMatrix
    }

    override fun collectResultIO(
        matI: List<MatrixRowState>,
        matO: List<MatrixRowState>,
        matMarking: List<MatrixRowState>,
        orientation: Orientation
    ): ImportMatrixResult {
        return collectMatrixResult(
            matI = SimpleMatrix(matI.mapToTypedArray()),
            matO = SimpleMatrix(matO.mapToTypedArray()),
            matMarking = SimpleMatrix(matMarking.mapToTypedArray()),
            orientation = orientation
        )
    }

    override fun collectResultC(
        matC: List<MatrixRowState>,
        matMarking: List<MatrixRowState>,
        orientation: Orientation
    ): ImportMatrixResult {
        val simpleMatrixC = SimpleMatrix(matC.mapToTypedArray())

        return collectMatrixResult(
            matI = simpleMatrixC.mapValue { d ->
                when (d) {
                    -1.0 -> 1.0
                    else -> 0.0
                }
            },
            matO = simpleMatrixC.mapValue { d ->
                d.takeIf { d == 1.0 } ?: 0.0
            },
            matMarking = SimpleMatrix(matMarking.mapToTypedArray()),
            orientation = orientation
        )
    }

    private fun collectMatrixResult(
        matI: SimpleMatrix,
        matO: SimpleMatrix,
        matMarking: SimpleMatrix,
        orientation: Orientation
    ): ImportMatrixResult {
        val positionsNumber = matI.numRows
        val transitionNumber = matI.numCols

        validateResult(
            matI,
            matO
        )

        validateMarking(
            matMarking
        )

        return ImportMatrixResult(
            matI = matI,
            matO = matO,
            matMarking = matMarking,
            positionNames = generateNamesList("P", positionsNumber),
            transitionNames = generateNamesList("T", transitionNumber),
            orientation = orientation
        )
    }

    private fun validateResult(
        vararg matrices: SimpleMatrix
    ) {
        eachValue(*matrices) { value ->
            if (value != 0.0 && value != 1.0) {
                throw IllegalMatrixValueException(value)
            }
        }
    }

    private fun validateMarking(
        vararg matrices: SimpleMatrix
    ) {
        eachValue(*matrices) { value ->
            if (!(DoubleMath.isMathematicalInteger(value) && value >= 0)) {
                throw IllegalMatrixValueException(value)
            }
        }
    }

    private fun eachValue(
        vararg matrices: SimpleMatrix,
        callback: (Double) -> Unit
    ) {
        matrices.forEach { matrix ->
            matrix.forEach { value ->
                callback.invoke(value)
            }
        }
    }

    private fun generateNamesList(prefix: String, count: Int): List<String> {
        val list = mutableListOf<String>()
        repeat(count) {
            list.add("$prefix${it + 1}")
        }
        return list
    }
}