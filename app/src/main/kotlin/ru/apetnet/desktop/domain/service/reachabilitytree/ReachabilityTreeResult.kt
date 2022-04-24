package ru.apetnet.desktop.domain.service.reachabilitytree

import javafx.beans.property.StringProperty
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.pnutil.reachabilitytreefinder.model.ReachabilityTreeData
import ru.apetnet.pnutil.reachabilitytreefinder.model.RtMarking
import tornadofx.stringProperty
import java.util.*

data class ReachabilityTreeResult(
    val markings: MarkingInfo,
    val transitions: TransitionInfo
) {
    data class MarkingInfo(
        val columnList: List<String>,
        val rowList: List<MatrixRowState>
    )

    data class TransitionInfo(
        val columnList: List<Column> = Column.values().toList(),
        val rowList: List<MatrixRowState>
    ) {
        enum class Column {
            SOURCE,
            TRANSITION,
            RECEIVER
        }
    }
}

fun mapMarkingToInfo(
    positions: List<WorkspaceObjectBrief.Position>,
    map: Map<UUID, RtMarking>
): ReachabilityTreeResult.MarkingInfo {
    val rowList = mutableListOf<MatrixRowState>()

    map.values.forEach { item ->
        val state = MatrixRowState(
            id = item.name,
            values = item.value.map {
                val v = it.toInt()
                stringProperty(
                    if (v == -1) {
                        "ω"
                    } else {
                        "${it.toInt()}"
                    }
                )
            }
        )
        rowList.add(state)
    }

    return ReachabilityTreeResult.MarkingInfo(
        columnList = positions.map { it.name },
        rowList = rowList
    )
}

fun mapTransactionToInfo(
    data: ReachabilityTreeData
): ReachabilityTreeResult.TransitionInfo {
    val markingMap = data.markingMap
    val rowList = mutableListOf<MatrixRowState>()

    val columnList = ReachabilityTreeResult.TransitionInfo.Column.values().toList()

    data.transitionMap.values.forEachIndexed { index, item ->
        val state = MatrixRowState(
            id = (index + 1).toString(),
            values = columnList.map {
                when (it) {
                    ReachabilityTreeResult.TransitionInfo.Column.SOURCE -> {
                        getMarkingNamePropertyOrThrow(markingMap, item.source)
                    }
                    ReachabilityTreeResult.TransitionInfo.Column.RECEIVER -> {
                        getMarkingNamePropertyOrThrow(markingMap, item.receiver)
                    }
                    ReachabilityTreeResult.TransitionInfo.Column.TRANSITION -> {
                        stringProperty(item.name)
                    }
                }
            }
        )

        rowList.add(state)
    }

    return ReachabilityTreeResult.TransitionInfo(
        columnList = columnList,
        rowList = rowList
    )
}

private fun getMarkingNamePropertyOrThrow(map: Map<UUID, RtMarking>, id: UUID): StringProperty {
    val marking = checkNotNull(map[id])

    return stringProperty(marking.name)
}