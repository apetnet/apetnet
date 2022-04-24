package ru.apetnet.matutil.linearsolver.util

import com.google.common.math.DoubleMath
import ru.apetnet.matutil.linearsolver.exception.IsNotMathematicalIntegerException
import ru.apetnet.matutil.linearsolver.model.RootEquation

object MapperUtil {
    fun mapRootListToMap(rootList: List<RootEquation>): Map<Int, Double> {
        val rootMap = mutableMapOf<Int, Double>()

        rootList.forEach {
            rootMap[it.index] = it.value.toDouble()
        }
        return rootMap
    }

    fun mapRootMapToList(rootMap: Map<Int, Double>): List<RootEquation> {
        val rootList = mutableListOf<RootEquation>()

        repeat(rootMap.values.size) { index ->
            val root = checkNotNull(rootMap[index])

            if (!DoubleMath.isMathematicalInteger(root)) {
                throw IsNotMathematicalIntegerException(root)
            }

            rootList.add(
                RootEquation(
                    index = index,
                    value = root.toInt()
                )
            )
        }

        return rootList
    }
}