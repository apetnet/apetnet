package ru.apetnet.matutil.linearsolver.impl

import org.ejml.data.DMatrixRMaj
import org.ejml.dense.row.CommonOps_DDRM
import org.ejml.simple.SimpleMatrix
import ru.apetnet.ext.ejml.getRow
import ru.apetnet.ext.ejml.lastRow
import ru.apetnet.ext.ejml.numCols
import ru.apetnet.ext.ejml.numRows
import ru.apetnet.matutil.linearsolver.api.HomogeneousLinearSolver
import ru.apetnet.matutil.linearsolver.exception.VerifyException
import ru.apetnet.matutil.linearsolver.model.EquationSolution
import ru.apetnet.matutil.linearsolver.model.RootEquation
import ru.apetnet.matutil.linearsolver.model.SolutionResult

class HomogeneousLinearSolverImpl : HomogeneousLinearSolver {

    override fun solve(
        matrix: SimpleMatrix,
    ): EquationSolution {
        val result = prepareSolution(
            matrix = matrix,
            rref = CommonOps_DDRM.rref(
                matrix.getMatrix(),
                -1,
                null
            )
        )
        val roots = collectRoots(result)

        return EquationSolution(listOf(roots))
    }

    private fun prepareSolution(
        matrix: SimpleMatrix,
        rref: DMatrixRMaj
    ): SolutionResult {
        val zeroFactorSet = mutableSetOf<Int>()
        val zeroSet = mutableSetOf<Int>()
        val equalSet = mutableSetOf<Int>()

        for (rowId in rref.lastRow downTo 0) {
            val row = rref.getRow(rowId)

            val indicesSet = mutableSetOf<Int>()

            row.forEachIndexed { i, v ->
                when {
                    v == 0.0 -> {
                        zeroFactorSet.add(i)
                    }
                    !zeroSet.contains(i) -> {
                        indicesSet.add(i)
                    }
                }
            }

            val tempIndices = mutableListOf<Int>().also {
                it.addAll(indicesSet)
            }

            while (tempIndices.isNotEmpty()) {
                val i1 = tempIndices[0]
                var i2: Int? = null

                for (i in 1..tempIndices.lastIndex) {
                    val i2t = tempIndices[i]
                    if (row[i2t] == row[i1] * -1) {
                        i2 = i2t
                        tempIndices.removeAt(i)
                        break
                    }
                }

                i2?.let {
                    equalSet.addAll(
                        listOf(i1, i2)
                    )
                } ?: run {
                    if (equalSet.contains(i1)) {
                        zeroSet.addAll(equalSet)
                        equalSet.clear()
                    } else {
                        zeroSet.add(i1)
                    }
                }
                tempIndices.removeAt(0)
            }
        }

        zeroFactorSet.forEach {
            if (!zeroSet.contains(it) && !equalSet.contains(it)) {
                equalSet.add(it)
            }
        }

        return verifySolution(
            originalMatrix = matrix,
            result = SolutionResult(
                zeroIndices = zeroSet.toList(),
                equalIndices = equalSet.toList(),
                notEqualIndices = listOf()
            )
        )
    }

    private fun verifySolution(
        originalMatrix: SimpleMatrix,
        result: SolutionResult
    ): SolutionResult {
        repeat(originalMatrix.numRows) { rowId ->
            var sum = 0.0

            repeat(originalMatrix.numCols) { colId ->
                val variable = when {
                    result.zeroIndices.contains(colId) -> 0
                    result.equalIndices.contains(colId) -> 1
                    else -> throw IllegalStateException(result.toString())

                }
                sum += originalMatrix.get(rowId, colId) * variable
            }

            if (sum != 0.0) {
                throw VerifyException(
                    matrix = originalMatrix,
                    solution = result.toString()
                )
            }
        }
        return result
    }

    private fun collectRoots(
        data: SolutionResult
    ): List<RootEquation> {
        val zeroList = mapToRootEquationList(
            indices = data.zeroIndices,
            root = 0
        )

        val oneList = mapToRootEquationList(
            indices = data.equalIndices,
            root = 1
        )

        return (zeroList + oneList).sortedBy { it.index }
    }

    private fun mapToRootEquationList(
        indices: List<Int>,
        root: Int
    ): List<RootEquation> {
        return indices.map { index ->
            RootEquation(
                index = index,
                value = root
            )
        }
    }
}