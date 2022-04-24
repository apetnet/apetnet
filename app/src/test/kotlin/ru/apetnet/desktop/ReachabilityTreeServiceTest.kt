package ru.apetnet.desktop

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import ru.apetnet.desktop.domain.service.reachabilitytree.ReachabilityTreeResult
import ru.apetnet.desktop.domain.service.reachabilitytree.ReachabilityTreeResult.TransitionInfo
import ru.apetnet.desktop.helper.assertEquals
import ru.apetnet.desktop.mock.PnMock
import ru.apetnet.desktop.model.MockedPetriNet
import ru.apetnet.desktop.model.toRowStateMatrix
import ru.apetnet.desktop.service.matrix.helpers.MatrixHelperServiceImpl
import ru.apetnet.desktop.service.matrix.reachabilitytree.ReachabilityTreeServiceImpl
import ru.apetnet.pnutil.reachabilitytreefinder.impl.ReachabilityTreeFinderImpl

class ReachabilityTreeServiceTest {
    private val service = ReachabilityTreeServiceImpl(
        matrixHelper = MatrixHelperServiceImpl(),
        reachabilityTreeFinder = ReachabilityTreeFinderImpl()
    )

    @Test
    fun collectReachabilityTreeTest() {
        PnMock.netList.forEach {
            testCollectReachabilityTree(it) {
                "Network: ${it.networkObject}"
            }
        }
    }

    private fun testCollectReachabilityTree(
        petriNet: MockedPetriNet,
        lazyMessage: () -> Any = {}
    ) {
        val reachabilityTree = petriNet.reachabilityTree

        val (markings, transitions) = if (petriNet.hasOmega) {
            // TODO: Это можно будет убрать, когда омеги будут нормально обрабатываться.
            assertThrows<StackOverflowError> {
                service.collectReachabilityTree(
                    items = petriNet.objects
                )
            }
            return
        } else {
            service.collectReachabilityTree(
                items = petriNet.objects
            )
        }

        testMarkingColumnTitles(
            petriNet = petriNet,
            markingInfo = markings
        )

        testTransitionColumnTitles(
            transitionInfo = transitions
        )

        assertEquals(
            a = markings.rowList,
            b = reachabilityTree.markings.toRowStateMatrix(isMarking = true),
            isStrict = false,
            lazyMessage = lazyMessage
        )

        assertEquals(
            a = transitions.rowList,
            b = reachabilityTree.transitions.toRowStateMatrix(),
            isStrict = false,
            lazyMessage = lazyMessage
        )
    }

    private fun testMarkingColumnTitles(
        petriNet: MockedPetriNet,
        markingInfo: ReachabilityTreeResult.MarkingInfo
    ) {
        assert(markingInfo.columnList == petriNet.positionNames)
    }

    private fun testTransitionColumnTitles(
        transitionInfo: TransitionInfo
    ) {
        assert(
            transitionInfo.columnList == TRANSITION_COLUMN_LIST
        )
    }

    companion object {
        private val TRANSITION_COLUMN_LIST: List<TransitionInfo.Column> = listOf(
            TransitionInfo.Column.SOURCE,
            TransitionInfo.Column.TRANSITION,
            TransitionInfo.Column.RECEIVER,
        )
    }
}