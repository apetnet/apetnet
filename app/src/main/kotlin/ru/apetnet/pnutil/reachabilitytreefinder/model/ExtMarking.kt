package ru.apetnet.pnutil.reachabilitytreefinder.model

data class ExtMarking(
    val array: DoubleArray,
    val isOmega: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExtMarking

        if (!array.contentEquals(other.array)) return false
        if (isOmega != other.isOmega) return false

        return true
    }

    override fun hashCode(): Int {
        var result = array.contentHashCode()
        result = 31 * result + isOmega.hashCode()
        return result
    }
}