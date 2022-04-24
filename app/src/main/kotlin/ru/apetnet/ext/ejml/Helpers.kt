package ru.apetnet.ext.ejml

import org.ejml.simple.SimpleMatrix

fun simpleMatrixOf(): SimpleMatrix {
    return simpleMatrixOf(intArrayOf())
}

fun simpleMatrixOf(vararg rows: IntArray): SimpleMatrix {
    return SimpleMatrix(
        rows.map { it.toDoubleArray() }.toTypedArray()
    )
}