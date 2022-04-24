package ru.apetnet.desktop.service.matrix.matrixanalysis

import com.google.inject.Inject
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.domain.service.matrixanalysis.MurataAnalysisSolution
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.exception.PredefinedErrorException
import ru.apetnet.desktop.service.matrix.helpers.MatrixHelperService
import ru.apetnet.ext.ejml.getRow
import ru.apetnet.ext.ejml.numCols
import ru.apetnet.ext.ejml.numRows
import ru.apetnet.matutil.linearsolver.api.HomogeneousLinearSolver
import ru.apetnet.matutil.linearsolver.model.RootEquation
import ru.apetnet.matutil.linearsolver.util.VerificationUtil.isValidRoots
import ru.apetnet.matutil.linearsolver.util.VerificationUtil.verifySolution
import tornadofx.stringProperty

class MurataAnalysisServiceImpl @Inject constructor(
    private val matrixHelper: MatrixHelperService,
    private val homogeneousLinearSolver: HomogeneousLinearSolver
) : MurataAnalysisService {
    /*
     * Name: Petri nets: Properties, analysis and applications
     * Author: Tadao Murata
     * Link: https://inst.eecs.berkeley.edu/~ee249/fa07/discussions/PetriNets-Murata.pdf
     */
    override fun collectSolution(
        items: List<WorkspaceObjectBrief>,
        withVerifying: Boolean
    ): MurataAnalysisSolution {
        val matC = matrixHelper.collectIncidenceMatrix(items).matC

        if (withVerifying) {
            verifyNetwork(matC)
        }

        val rootsX = collectRoots(matC)
        val rootsY = collectRoots(matC.transpose())

        return MurataAnalysisSolution(
            colNamesX = mapToColName(rootsX, "x"),
            colNamesY = mapToColName(rootsY, "y"),
            solutionX = collectMatrixRowState(
                solution = rootsX
            ),
            solutionY = collectMatrixRowState(
                solution = rootsY
            ),
            isConsistent = rootsX.all { it.value > 0 },
            isConservative = rootsY.all { it.value > 0 }
        )
    }

    private fun collectMatrixRowState(
        solution: List<RootEquation>
    ): List<MatrixRowState> {

        return listOf(
            MatrixRowState(
                id = "1",
                values = solution.map { stringProperty("${it.value}") }
            )
        )
    }

    private fun collectRoots(
        matC: SimpleMatrix
    ): List<RootEquation> {
        val solutionList = homogeneousLinearSolver.solve(
            matrix = matC
        ).solutionList
            .filter(::isValidRoots)

        val arr = IntArray(matC.numCols)

        solutionList.forEach { roots ->
            roots.forEach { eq ->
                arr[eq.index] += eq.value
            }
        }

        val rootList = mapToRootEquationList(arr)

        verifySolution(
            originalMatrix = matC,
            rootList = rootList
        )

        return rootList
    }

    private fun mapToRootEquationList(
        array: IntArray
    ): List<RootEquation> {
        val result = mutableListOf<RootEquation>()

        for (index in array.indices) {
            result.add(
                RootEquation(
                    index = index,
                    value = array[index]
                )
            )
        }

        return result
    }

    private fun mapToColName(roots: List<RootEquation>, prefix: String): List<String> {
        return roots.map { "$prefix${it.index + 1}" }
    }

    private fun verifyNetwork(mat: SimpleMatrix) {
        for (rowId in 0 until mat.numRows) {
            val row = mat.getRow(rowId)
            val hasInputOutput = row.any { it == 1.0 } && row.any { it == -1.0 }

            if (!hasInputOutput) {
                throw PredefinedErrorException("container.error.not_strongly_connected")
            }
        }
    }
}