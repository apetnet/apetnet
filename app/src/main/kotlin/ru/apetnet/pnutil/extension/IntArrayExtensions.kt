package ru.apetnet.pnutil.extension

import ru.apetnet.pnutil.exception.NotEnoughTokensThrowable

internal fun IntArray.findIndicesMoreThanZero(): List<Int> {
    return findIndices { it > 0 }
}

internal fun IntArray.removeToken(pId: Int) {
    if (this[pId] > 0.0) {
        this[pId] -= 1
    } else {
        throw NotEnoughTokensThrowable()
    }
}

internal fun IntArray.addToken(pId: Int) {
    this[pId] += 1
}

internal fun IntArray.findAllP(): List<Int> {
    return findIndices { it > 0.0 }
}

inline fun IntArray.findIndices(predicate: (Int) -> Boolean): List<Int> {
    val list = mutableListOf<Int>()
    forEachIndexed { i, v ->
        if (predicate(v)) {
            list.add(i)
        }
    }
    return list.toList()
}