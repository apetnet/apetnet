package ru.apetnet.matutil.linearsolver.util

import org.ejml.simple.SimpleMatrix
import ru.apetnet.ext.ejml.numCols
import ru.apetnet.ext.ejml.numRows
import ru.apetnet.matutil.linearsolver.exception.VerifyException
import ru.apetnet.matutil.linearsolver.model.RootEquation
import ru.apetnet.matutil.linearsolver.util.MapperUtil.mapRootListToMap

object VerificationUtil {
    fun isValidRoots(
        rootList: List<RootEquation>
    ): Boolean {
        val values = rootList.map { it.value }
        return !values.any { it < 0 }
    }

    fun verifySolution(
        originalMatrix: SimpleMatrix,
        rootList: List<RootEquation>
    ) {
        verifySolution(
            originalMatrix = originalMatrix,
            rootMap = mapRootListToMap(rootList)
        )
    }

    fun verifySolution(
        originalMatrix: SimpleMatrix,
        rootMap: Map<Int, Double>
    ) {
        repeat(originalMatrix.numRows) { rowId ->
            var sum = 0.0

            repeat(originalMatrix.numCols) { colId ->
                sum += originalMatrix.get(rowId, colId) * checkNotNull(rootMap[colId])
            }

            if (sum != 0.0) {
                throw VerifyException(
                    matrix = originalMatrix,
                    solution = rootMap.toList().toString()
                )
            }
        }
    }
}