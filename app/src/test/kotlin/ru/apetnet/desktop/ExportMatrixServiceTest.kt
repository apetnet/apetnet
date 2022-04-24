package ru.apetnet.desktop

import org.junit.jupiter.api.Test
import ru.apetnet.desktop.mock.PnMock
import ru.apetnet.desktop.helper.assertEquals
import ru.apetnet.desktop.model.MockedPetriNet
import ru.apetnet.desktop.model.toRowState
import ru.apetnet.desktop.service.matrix.exportmatrix.ExportMatrixServiceImpl
import ru.apetnet.desktop.service.matrix.helpers.MatrixHelperServiceImpl

internal class ExportMatrixServiceTest {
    private val service = ExportMatrixServiceImpl(
        matrixHelper = MatrixHelperServiceImpl()
    )

    @Test
    fun collectExportMatrixTest() {
        PnMock.netList.forEachIndexed { index, net ->
            testCollectExportMatrix(net) {
                "Network: ${index + 1}"
            }
        }
    }

    private fun testCollectExportMatrix(
        petriNet: MockedPetriNet,
        lazyMessage: () -> Any = {}
    ) {
        val originalState = petriNet.toRowState()

        val exportedMatrix = service.collectExportMatrix(
            items = petriNet.objects
        )

        listOf(
            originalState.matI to exportedMatrix.matI,
            originalState.matO to exportedMatrix.matO,
            originalState.matC to exportedMatrix.matC,
            originalState.matMarking to exportedMatrix.marking,
        ).forEach { (original, exported) ->
            assertEquals(
                a = original,
                b = exported,
                lazyMessage = lazyMessage
            )
        }
    }
}