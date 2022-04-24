package ru.apetnet.pnutil.reachabilitytreefinder.model

import java.util.*

data class ReachabilityTreeData(
    val markingMap: Map<UUID, RtMarking>,
    val transitionMap: Map<UUID, RtTransition>
)

data class RtMarking(
    val id: Long,
    val value: DoubleArray
) {
    val name: String get() = "M${id + 1}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RtMarking

        if (id != other.id) return false
        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + value.contentHashCode()
        return result
    }
}

data class RtTransition(
    val id: Int,
    val causedTransitionId: Int,
    val source: UUID,
    val receiver: UUID,
) {
    val name: String get() = "T${causedTransitionId + 1}"
}