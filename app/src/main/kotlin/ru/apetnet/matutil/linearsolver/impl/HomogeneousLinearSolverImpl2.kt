package ru.apetnet.matutil.linearsolver.impl

import org.ejml.data.DMatrixRMaj
import org.ejml.dense.row.CommonOps_DDRM
import org.ejml.simple.SimpleMatrix
import org.paukov.combinatorics3.Generator
import ru.apetnet.ext.ejml.getRow
import ru.apetnet.ext.ejml.lastCol
import ru.apetnet.ext.ejml.lastRow
import ru.apetnet.matutil.linearsolver.api.HomogeneousLinearSolver
import ru.apetnet.matutil.linearsolver.exception.DuplicateIndicesException
import ru.apetnet.matutil.linearsolver.exception.RootAlreadyExistsException
import ru.apetnet.matutil.linearsolver.model.EquationSolution
import ru.apetnet.matutil.linearsolver.util.MapperUtil
import ru.apetnet.matutil.linearsolver.util.VerificationUtil.verifySolution
import java.util.stream.Collectors

class HomogeneousLinearSolverImpl2 : HomogeneousLinearSolver {

    override fun solve(
        matrix: SimpleMatrix,
    ): EquationSolution {
        val rref = CommonOps_DDRM.rref(
            matrix.getMatrix(),
            -1,
            null
        )
        rref.print()

        return collectSolution(
            matrix = matrix,
            rref = rref,
            predefinedVarList = collectPredefinedVariables(rref)
        )
    }

    /*
     * Collect
     */
    private fun collectSolution(
        matrix: SimpleMatrix,
        rref: DMatrixRMaj,
        predefinedVarList: List<Int>,
    ): EquationSolution {
        val permutationLength = predefinedVarList.size

        val rootsList = Generator.permutation(0.0, 1.0)
            .withRepetitions(permutationLength)
            .stream()
            .parallel()
            .map { list ->
                mapToPredefinedRootsMap(
                    predefinedVarList = predefinedVarList,
                    rootList = list
                )
            }
            .map { freeVarRootMap ->
                findRoots(
                    matrix = matrix,
                    rref = rref,
                    predefinedRoots = freeVarRootMap
                )
            }
            .map(MapperUtil::mapRootMapToList)
            .collect(Collectors.toList())

        return EquationSolution(rootsList)
    }

    private fun collectPredefinedVariables(
        rref: DMatrixRMaj
    ): List<Int> {
        val freeVarList = collectFreeVariables(rref)
        val zeroVarList = collectZeroVariables(rref)

        val predefinedVarList = freeVarList + zeroVarList

        verifyPredefinedVarList(predefinedVarList)

        return predefinedVarList
    }

    private fun collectFreeVariables(
        rref: DMatrixRMaj
    ): List<Int> {
        val freeVarList = mutableListOf<Int>()

        val lastCol = rref.lastCol

        var rowId = rref.lastRow

        var firstFreeVarIndex = -1

        while (firstFreeVarIndex == -1 && rowId >= 0) {
            val row = rref.getRow(rowId)
            val basisVarIndex = row.indexOfFirst { it != 0.0 }

            if (basisVarIndex != -1) {
                firstFreeVarIndex = basisVarIndex + 1

                for (index in firstFreeVarIndex..lastCol) {
                    freeVarList.add(index)
                }
            }
            rowId--
        }
        return freeVarList
    }

    private fun collectZeroVariables(
        rref: DMatrixRMaj
    ): List<Int> {
        val zeroVarList = mutableListOf<Int>()

        var colId = 0

        var isZero = true

        while (colId < rref.numCols && isZero) {
            var rowId = 0

            while (rowId < rref.numRows && isZero) {
                isZero = rref.get(rowId, colId) == 0.0
                rowId++
            }

            if (isZero) {
                zeroVarList.add(colId)
            }
            colId++
        }

        return zeroVarList
    }


    /*
     * Find
     */
    private fun findRoots(
        matrix: SimpleMatrix,
        rref: DMatrixRMaj,
        predefinedRoots: Map<Int, Double>
    ): Map<Int, Double> {
        val rootMap = predefinedRoots.toMutableMap()

        for (rowId in rref.lastRow downTo 0) {
            val row = rref.getRow(rowId)
            val basisVarIndex = row.indexOfFirst { it != 0.0 }

            if (basisVarIndex != -1) {
                val freeVarIndex = basisVarIndex + 1

                var basisRoot = 0.0

                for (index in freeVarIndex..rref.lastCol) {
                    val freeRoot = checkNotNull(rootMap[index])
                    basisRoot += (freeRoot * row[index]) * -1
                }

                if (!rootMap.contains(basisVarIndex)) {
                    rootMap[basisVarIndex] = basisRoot
                } else {
                    throw RootAlreadyExistsException(basisVarIndex)
                }
            }
        }

        verifySolution(
            originalMatrix = matrix,
            rootMap = rootMap
        )
        return rootMap
    }

    /*
     * Mappers
     */
    private fun mapToPredefinedRootsMap(
        predefinedVarList: List<Int>,
        rootList: MutableList<Double>
    ): Map<Int, Double> {
        val predefinedRootMap = mutableMapOf<Int, Double>()

        predefinedVarList.forEachIndexed { index, value ->
            predefinedRootMap[value] = rootList[index]
            predefinedRootMap[value] = rootList[index]
        }

        return predefinedRootMap
    }

    /*
     * Verification
     */
    private fun verifyPredefinedVarList(
        predefinedVarList: List<Int>
    ) {
        val distinctList = predefinedVarList.distinct()

        if (distinctList.size != predefinedVarList.size) {
            throw DuplicateIndicesException(
                original = predefinedVarList,
                distinct = distinctList
            )
        }
    }
}