package ru.apetnet.pnutil.reachabilitytreefinder.util

import org.ejml.simple.SimpleMatrix

internal data class Network(
    val matI: SimpleMatrix,
    val matO: SimpleMatrix,
    val marking: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Network

        if (matI != other.matI) return false
        if (matO != other.matO) return false
        if (!marking.contentEquals(other.marking)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = matI.hashCode()
        result = 31 * result + matO.hashCode()
        result = 31 * result + marking.contentHashCode()
        return result
    }
}