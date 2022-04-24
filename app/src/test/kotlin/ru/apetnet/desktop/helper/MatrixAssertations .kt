package ru.apetnet.desktop.helper

import org.ejml.UtilEjml
import org.ejml.dense.row.MatrixFeatures_DDRM
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.colsNum
import ru.apetnet.desktop.domain.ui.matrix.rowsNum
import ru.apetnet.ext.ejml.fillRandom
import ru.apetnet.ext.ejml.numCols
import ru.apetnet.ext.ejml.numRows

internal fun assertEquals(
    a: SimpleMatrix,
    b: SimpleMatrix,
    lazyMessage: () -> Any = {}
) {
    val randomMatrix = SimpleMatrix(
        a.numRows,
        a.numCols
    ).also {
        it.fillRandom(10, 20)
    }

    assert(
        MatrixFeatures_DDRM.isEquals(
            a.getMatrix(),
            b.getMatrix(),
            UtilEjml.TEST_F64
        ),
        lazyMessage
    )

    assert(
        !MatrixFeatures_DDRM.isEquals(
            randomMatrix.getMatrix(),
            b.getMatrix(),
            UtilEjml.TEST_F64
        ),
        lazyMessage
    )
}

internal fun assertEquals(
    a: List<MatrixRowState>,
    b: List<MatrixRowState>,
    isStrict: Boolean = true,
    lazyMessage: () -> Any = {}
) {
    assert(
        isEquals(
            a = a,
            b = b,
            isStrict = isStrict
        ),
        lazyMessage
    )
    assert(
        !isEquals(
            a = a,
            b = b.toMutableList().also {
                if (it.isNotEmpty()) {
                    it.removeAt(0)
                }
                else {
                    it.add(
                        rowStateMatrixOf(intArrayOf(1, 2)).first()
                    )
                }
            },
            isStrict = isStrict
        ),
        lazyMessage
    )
}

private fun isEquals(
    a: List<MatrixRowState>,
    b: List<MatrixRowState>,
    isStrict: Boolean = true
): Boolean {
    return if (isStrict) {
        isEqualsStrict(a, b)
    } else {
        val aValues = a.map { row ->
            row.rowValues.map {
                it.value
            }
        }
        val bValues = b.map { row ->
            row.rowValues.map {
                it.value
            }
        }

        println("{")
        println(aValues)
        println("-----")
        println(bValues)
        println("}")

        aValues.containsAll(bValues) && bValues.containsAll(aValues)
    }
}

private fun isEqualsStrict(
    a: List<MatrixRowState>,
    b: List<MatrixRowState>
): Boolean {
    val rowsNum = a.rowsNum
    val colsNum = a.colsNum

    if (rowsNum != b.rowsNum || colsNum != b.colsNum) {
        return false
    }

    repeat(rowsNum) { rowId ->
        val rowA = a[rowId]
        val rowB = b[rowId]

        if (rowA.rowId != rowB.rowId) {
            return false
        }

        repeat(colsNum) { colId ->
            val rowValuesA = rowA.rowValues
            val rowValuesB = rowB.rowValues

            if (rowValuesA[colId].value != rowValuesB[colId].value) {
                return false
            }
        }
    }

    return true
}