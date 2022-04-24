package ru.apetnet.desktop

import javafx.geometry.Orientation
import org.ejml.simple.SimpleMatrix
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.mock.PnMock
import ru.apetnet.desktop.helper.assertEquals
import ru.apetnet.desktop.model.toRowState
import ru.apetnet.desktop.model.toSimple
import ru.apetnet.desktop.service.matrix.importmatrix.ImportMatrixServiceImpl
import ru.apetnet.desktop.util.MatrixRowStateUtil.isEqual
import ru.apetnet.desktop.util.MatrixRowStateUtil.getRandomList
import ru.apetnet.desktop.util.MatrixRowStateUtil.hasEntry
import ru.apetnet.desktop.util.RndUtil

internal class ImportMatrixServiceTest {
    private val service = ImportMatrixServiceImpl()
    private val list = PnMock.netList

    @Test
    fun collectResultIOTest() {
        val mockMatrix = list[0]
        val mockRowState = mockMatrix.toRowState()
        val mockSimple = mockMatrix.toSimple()

        val (matI, matO, matMarking) = service.collectResultIO(
            matI = mockRowState.matI,
            matO = mockRowState.matO,
            matMarking = mockRowState.matMarking,
            orientation = Orientation.HORIZONTAL
        )

        listOf(
            matI to mockSimple.matI,
            matO to mockSimple.matO,
            matMarking to mockSimple.matMarking
        ).forEach { (collected, original) ->
            assertEquals(
                collected,
                original
            )
        }
    }

    @Test
    fun collectResultCTest() {
        val mockMatrix = list[0]
        val mockRowState = mockMatrix.toRowState()
        val mockSimple = mockMatrix.toSimple()

        val (matI, matO, matMarking) = service.collectResultC(
            matC = mockRowState.matC,
            matMarking = mockRowState.matMarking,
            orientation = Orientation.HORIZONTAL
        )

        val matC = matO - matI

        listOf(
            matI to mockSimple.matI,
            matO to mockSimple.matO,
            matC to mockSimple.matC,
            matMarking to mockSimple.matMarking
        ).forEach { (collected, original) ->
            assertEquals(
                collected,
                original
            )
        }
    }

    @Test
    @RepeatedTest(5)
    fun randomCollectMatrixTest() {
        val rowsNum = RndUtil.inRange(6, 12)
        val colsNum = rowsNum / 2
        val range = IntRange(-1, 1)

        val originalMatrix = getRandomList(
            rowsNum = rowsNum,
            colsNum = colsNum,
            range = range
        )

        val enlargedMatrix = service.collectMatrix(
            list = originalMatrix,
            rowsNum = rowsNum * 2,
            colsNum = colsNum * 2
        )

        assert(
            hasEntry(
                firstList = originalMatrix,
                secondList = enlargedMatrix
            )
        )

        val reducedMatrix = service.collectMatrix(
            list = enlargedMatrix,
            rowsNum = rowsNum,
            colsNum = colsNum
        )

        assert(
            hasEntry(
                firstList = enlargedMatrix,
                secondList = reducedMatrix
            )
        )

        assert(reducedMatrix == originalMatrix)
    }

    @Test
    @RepeatedTest(5)
    fun randomCollectResultIOTest() {
        val rowsNum = RndUtil.inRange(6, 12)
        val colsNum = rowsNum / 2
        val range = IntRange(0, 1)

        val matRowStateI = getRandomList(
            rowsNum = rowsNum,
            colsNum = colsNum,
            range = range
        )

        val matRowStateO = getRandomList(
            rowsNum = rowsNum,
            colsNum = colsNum,
            range = range
        )

        val matRowStateMarking = getRandomList(
            rowsNum = 1,
            colsNum = colsNum,
            range = range
        )

        val (matI, matO, matMarking) = service.collectResultIO(
            matI = matRowStateI,
            matO = matRowStateO,
            matMarking = matRowStateMarking,
            orientation = Orientation.HORIZONTAL
        )

        assetMatrixIO(
            matRowStateI = matRowStateI,
            matRowStateO = matRowStateO,
            matRowStateMarking = matRowStateMarking,
            matI = matI,
            matO = matO,
            matMarking = matMarking,
        )
    }

    @Test
    @RepeatedTest(5)
    fun randomCollectResultCTest() {
        val rowsNum = RndUtil.inRange(6, 12)
        val colsNum = rowsNum / 2

        val matRowStateC = getRandomList(
            rowsNum = rowsNum,
            colsNum = colsNum,
            range = IntRange(-1, 1)
        )

        val matRowStateMarking = getRandomList(
            rowsNum = 1,
            colsNum = colsNum,
            range = IntRange(0, 1)
        )

        val (matI, matO, matMarking) = service.collectResultC(
            matC = matRowStateC,
            matMarking = matRowStateMarking,
            orientation = Orientation.HORIZONTAL
        )

        assetMatrixC(
            matRowStateC = matRowStateC,
            matRowStateMarking = matRowStateMarking,
            matC = matO - matI,
            matMarking = matMarking
        )
    }

    private fun assetMatrixC(
        matRowStateC: List<MatrixRowState>,
        matRowStateMarking: List<MatrixRowState>,
        matC: SimpleMatrix,
        matMarking: SimpleMatrix
    ) {
        listOf(
            matRowStateC to matC,
            matRowStateMarking to matMarking
        ).forEach { (matRowState, mat) ->
            assert(
                value = isEqual(
                    matrix = mat,
                    state = matRowState
                )
            )
        }
    }

    private fun assetMatrixIO(
        matRowStateI: List<MatrixRowState>,
        matRowStateO: List<MatrixRowState>,
        matRowStateMarking: List<MatrixRowState>,
        matI: SimpleMatrix,
        matO: SimpleMatrix,
        matMarking: SimpleMatrix
    ) {
        listOf(
            matRowStateI to matI,
            matRowStateO to matO,
            matRowStateMarking to matMarking
        ).forEach { (matRowState, mat) ->
            assert(
                value = isEqual(
                    matrix = mat,
                    state = matRowState
                )
            )
        }
    }
}