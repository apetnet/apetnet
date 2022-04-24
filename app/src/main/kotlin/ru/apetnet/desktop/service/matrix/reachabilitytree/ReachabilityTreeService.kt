package ru.apetnet.desktop.service.matrix.reachabilitytree

import ru.apetnet.desktop.domain.service.reachabilitytree.ReachabilityTreeResult
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief

interface ReachabilityTreeService {
    fun collectReachabilityTree(
        items: List<WorkspaceObjectBrief>
    ): ReachabilityTreeResult
}