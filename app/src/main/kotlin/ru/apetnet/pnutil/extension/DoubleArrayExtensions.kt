package ru.apetnet.pnutil.extension

import ru.apetnet.pnutil.exception.NotEnoughTokensThrowable

internal fun DoubleArray.findIndicesMoreThanZero(): List<Int> {
    return findIndices { it > 0 }
}

internal fun DoubleArray.removeToken(pId: Int) {
    if (this[pId] > 0.0) {
        this[pId] -= 1.0
    } else {
        throw NotEnoughTokensThrowable()
    }
}

internal fun DoubleArray.addToken(pId: Int) {
    this[pId] += 1.0
}

internal fun DoubleArray.moveToken(from: Int, to: Int) {
    removeToken(from)
    addToken(to)
}

internal fun DoubleArray.findAllP(): List<Int> {
    return findIndices { it > 0.0 }
}

inline fun DoubleArray.findIndices(predicate: (Double) -> Boolean): List<Int> {
    val list = mutableListOf<Int>()
    forEachIndexed { i, v ->
        if (predicate(v)) {
            list.add(i)
        }
    }
    return list.toList()
}

internal fun DoubleArray.toIntArray(): IntArray {
    val arr = IntArray(size)

    forEachIndexed { index, value ->
        arr[index] = value.toInt()
    }

    return arr
}