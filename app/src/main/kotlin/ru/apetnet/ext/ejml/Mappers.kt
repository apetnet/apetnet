package ru.apetnet.ext.ejml

import org.ejml.simple.SimpleMatrix
import ru.apetnet.ext.independent.kotlin.print

fun IntArray.toDoubleArray(): DoubleArray {
    val arr = DoubleArray(size)

    forEachIndexed { index, value ->
        arr[index] = value.toDouble()
    }

    return arr
}

fun List<DoubleArray>.toSimpleMatrix(): SimpleMatrix {
    return SimpleMatrix(toTypedArray())
}