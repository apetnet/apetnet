package ru.apetnet.desktop.service.matrix.helpers

import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.domain.service.exportmatrix.IncidenceMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief

class MatrixHelperServiceImpl : MatrixHelperService {
    override fun collectIncidenceMatrix(
        items: List<WorkspaceObjectBrief>
    ): IncidenceMatrixResult {

        return collectIncidenceMatrix(
            items = items,
            arcs = items.filterIsInstance<WorkspaceObjectBrief.Arc>(),
            positions = items.filterIsInstance<WorkspaceObjectBrief.Position>(),
            transitions = items.filterIsInstance<WorkspaceObjectBrief.Transition>()
        )
    }

    override fun collectIncidenceMatrix(
        items: List<WorkspaceObjectBrief>,
        positions: List<WorkspaceObjectBrief.Position>,
        transitions: List<WorkspaceObjectBrief.Transition>,
        arcs: List<WorkspaceObjectBrief.Arc>
    ): IncidenceMatrixResult {
        val positionNumber = positions.size
        val transitionNumber = transitions.size

        val matI = SimpleMatrix(positionNumber, transitionNumber)
        val matO = SimpleMatrix(positionNumber, transitionNumber)

        val marking = SimpleMatrix(
            when {
                positionNumber > 0 -> 1
                else -> 0
            },
            positionNumber
        )

        positions.forEachIndexed { index, position ->
            marking.set(
                0,
                index,
                position.tokensNumber.toDouble()
            )
        }

        arcs.forEach { arc ->
            val source = items.find { it.id == arc.source }
            val receiver = items.find { it.id == arc.receiver }

            if (source != null && receiver != null) {
                val (sourceIndex, receiverIndex) = resolveIndex(
                    source = source,
                    receiver = receiver,
                    positions = positions,
                    transitions = transitions
                )

                when (source) {
                    is WorkspaceObjectBrief.Position -> {
                        matI.set(
                            sourceIndex,
                            receiverIndex,
                            1.0
                        )
                    }
                    is WorkspaceObjectBrief.Transition -> {
                        matO.set(
                            receiverIndex,
                            sourceIndex,
                            1.0
                        )
                    }
                    is WorkspaceObjectBrief.Arc -> {
                        throw IllegalStateException()
                    }
                }
            }
        }

        return IncidenceMatrixResult(
            matI = matI,
            matO = matO,
            matC = matO - matI,
            marking = marking
        )
    }

    private fun resolveIndex(
        source: WorkspaceObjectBrief,
        receiver: WorkspaceObjectBrief,
        positions: List<WorkspaceObjectBrief.Position>,
        transitions: List<WorkspaceObjectBrief.Transition>,
    ): Pair<Int, Int> {
        val (sourceList, receiverList) = when (source) {
            is WorkspaceObjectBrief.Position -> {
                positions to transitions
            }
            is WorkspaceObjectBrief.Transition -> {
                transitions to positions
            }
            is WorkspaceObjectBrief.Arc -> {
                throw IllegalStateException()
            }
        }

        return Pair(
            first = sourceList.indexOfFirst { it.id == source.id },
            second = receiverList.indexOfFirst { it.id == receiver.id }
        )
    }
}