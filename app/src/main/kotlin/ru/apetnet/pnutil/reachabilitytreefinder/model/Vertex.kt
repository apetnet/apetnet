package ru.apetnet.pnutil.reachabilitytreefinder.model

import ru.apetnet.pnutil.reachabilitytreefinder.util.VertexType
import java.util.*

internal data class Vertex(
    val id: UUID,
    val type: VertexType,
    val marking: DoubleArray,
    val inputT: Int,
    val isOmega: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vertex

        if (id != other.id) return false
        if (type != other.type) return false
        if (!marking.contentEquals(other.marking)) return false
        if (inputT != other.inputT) return false
        if (isOmega != other.isOmega) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + marking.contentHashCode()
        result = 31 * result + inputT
        result = 31 * result + isOmega.hashCode()
        return result
    }
}