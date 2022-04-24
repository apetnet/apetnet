package ru.apetnet.desktop

import org.junit.jupiter.api.Test
import ru.apetnet.desktop.helper.assertEquals
import ru.apetnet.desktop.mock.PnMock
import ru.apetnet.desktop.model.MockedPetriNet
import ru.apetnet.desktop.model.toAnalysisSolution
import ru.apetnet.desktop.service.matrix.helpers.MatrixHelperServiceImpl
import ru.apetnet.desktop.service.matrix.matrixanalysis.MurataAnalysisServiceImpl
import ru.apetnet.matutil.linearsolver.impl.HomogeneousLinearSolverImpl2

internal class MurataAnalysisServiceTest {
    private val service = MurataAnalysisServiceImpl(
        matrixHelper = MatrixHelperServiceImpl(),
        homogeneousLinearSolver = HomogeneousLinearSolverImpl2()
    )

    @Test
    fun collectSolutionTest() {
        PnMock.netList.forEach {
            println("Network: ${it.networkObject}")
            testCollectSolution(it) {
                "Network: ${it.networkObject}"
            }
        }
    }

    private fun testCollectSolution(
        petriNet: MockedPetriNet,
        lazyMessage: () -> Any = {}
    ) {
        val originalSolution = petriNet.murataSolution.toAnalysisSolution()

        val murataAnalysis = service.collectSolution(
            items = petriNet.objects,
            withVerifying = false
        )

        listOf(
            originalSolution.solutionX to murataAnalysis.solutionX,
            originalSolution.solutionY to murataAnalysis.solutionY,
        ).forEach { (original, analysis) ->
            assertEquals(
                a = original,
                b = analysis,
                isStrict = true,
                lazyMessage = lazyMessage
            )
        }

        listOf(
            originalSolution.colNamesX to murataAnalysis.colNamesX,
            originalSolution.colNamesY to murataAnalysis.colNamesY
        ).forEach { (original, analysis) ->
            assert(
                value = original == analysis,
                lazyMessage = lazyMessage
            )
        }

        listOf(
            originalSolution.isConsistent to murataAnalysis.isConsistent,
            originalSolution.isConservative to murataAnalysis.isConservative
        ).forEach { (original, analysis) ->
            assert(
                value = original == analysis,
                lazyMessage = lazyMessage
            )
        }
    }
}