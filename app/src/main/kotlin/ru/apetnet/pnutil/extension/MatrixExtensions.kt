package ru.apetnet.pnutil.extension

import org.ejml.simple.SimpleMatrix
import ru.apetnet.ext.ejml.getColumn
import ru.apetnet.ext.ejml.getRow

internal fun SimpleMatrix.postP(pId: Int): List<Int> {
    return getRow(pId).findIndices { it > 0.0 }
}

internal fun SimpleMatrix.postT(tId: Int): List<Int> {
    return getColumn(tId).findIndices { it > 0.0 }
}

fun SimpleMatrix.preT(tId: Int): List<Int> {
    return getColumn(tId).findIndices { it > 0.0 }
}