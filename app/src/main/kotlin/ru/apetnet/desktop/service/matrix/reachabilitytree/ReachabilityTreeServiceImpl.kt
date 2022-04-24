package ru.apetnet.desktop.service.matrix.reachabilitytree

import com.google.inject.Inject
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.domain.service.reachabilitytree.ReachabilityTreeResult
import ru.apetnet.desktop.domain.service.reachabilitytree.mapMarkingToInfo
import ru.apetnet.desktop.domain.service.reachabilitytree.mapTransactionToInfo
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.service.matrix.helpers.MatrixHelperService
import ru.apetnet.ext.ejml.getRow
import ru.apetnet.pnutil.reachabilitytreefinder.ReachabilityTreeFinder
import ru.apetnet.pnutil.reachabilitytreefinder.model.ReachabilityTreeData

class ReachabilityTreeServiceImpl @Inject constructor(
    private val matrixHelper: MatrixHelperService,
    private val reachabilityTreeFinder: ReachabilityTreeFinder,
) : ReachabilityTreeService {

    override fun collectReachabilityTree(
        items: List<WorkspaceObjectBrief>
    ): ReachabilityTreeResult {
        val matrix = matrixHelper.collectIncidenceMatrix(items)
        val positions = items.filterIsInstance<WorkspaceObjectBrief.Position>()

        val treeData = findReachabilityTree(
            matI = matrix.matI,
            matO = matrix.matO,
            marking = matrix.marking,
        )

        return ReachabilityTreeResult(
            markings = mapMarkingToInfo(
                positions = positions,
                map = treeData.markingMap
            ),
            transitions = mapTransactionToInfo(
               data = treeData
            )
        )
    }

    private fun findReachabilityTree(
        matI: SimpleMatrix,
        matO: SimpleMatrix,
        marking: SimpleMatrix,
    ): ReachabilityTreeData {
        return reachabilityTreeFinder.find(
            matI = matI,
            matO = matO,
            initialMarking = marking.getRow(0)
        )
    }
}