package ru.apetnet.ext.independent.kotlin

import kotlin.math.min

fun DoubleArray.print() {
    val arr = this

    println(
        buildString {
            append("[")
            arr.forEachIndexed { index, d ->
                append(d)
                if (index < arr.lastIndex) {
                    append(", ")
                }
            }
            append("]")
        }
    )
}

operator fun DoubleArray.plus(b: DoubleArray): DoubleArray {
    return appleOperation(
        operator = MathOperator.Plus,
        a = this,
        b = b
    )
}

operator fun DoubleArray.minus(b: DoubleArray): DoubleArray {
    return appleOperation(
        operator = MathOperator.Minus,
        a = this,
        b = b
    )
}

private fun appleOperation(
    operator: MathOperator,
    a: DoubleArray,
    b: DoubleArray
): DoubleArray {
    val resultSize = min(a.size, b.size)

    val output = DoubleArray(resultSize)

    repeat(resultSize) { index ->
        val valueA = a[index]
        val valueB = b[index]

        output[index] = when (operator) {
            MathOperator.Plus -> valueA + valueB
            MathOperator.Minus -> valueA - valueB
        }
    }

    return output
}

private enum class MathOperator {
    Plus,
    Minus
}