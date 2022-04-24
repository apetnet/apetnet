package ru.apetnet.desktop.domain.ui.reachabilitytree

import ru.apetnet.desktop.domain.service.reachabilitytree.ReachabilityTreeResult

data class ReachabilityTreeState(
    val colMarkingNames: List<String>,
    val colConnectionNames: List<String>
)

fun ReachabilityTreeResult.toState(): ReachabilityTreeState {
    return ReachabilityTreeState(
        colConnectionNames = transitions.columnList.map { it.toString() },
        colMarkingNames = markings.columnList
    )
}