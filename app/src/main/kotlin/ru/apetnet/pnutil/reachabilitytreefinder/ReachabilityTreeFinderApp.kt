package ru.apetnet.pnutil.reachabilitytreefinder

import ru.apetnet.ext.ejml.simpleMatrixOf
import ru.apetnet.ext.ejml.toDoubleArray
import ru.apetnet.pnutil.reachabilitytreefinder.impl.ReachabilityTreeFinderImpl
import ru.apetnet.pnutil.reachabilitytreefinder.util.Network

internal object ReachabilityTreeFinderApp {
    private val finder: ReachabilityTreeFinder = ReachabilityTreeFinderImpl()

    @JvmStatic
    fun main(args: Array<String>) {
        val networkList = listOf(
            Network(
                matI = simpleMatrixOf(
                    intArrayOf(1, 1, 0),
                    intArrayOf(0, 0, 1),
                    intArrayOf(0, 0, 1)
                ),
                matO = simpleMatrixOf(
                    intArrayOf(0, 0, 1),
                    intArrayOf(1, 0, 0),
                    intArrayOf(0, 1, 0)
                ),
                marking = intArrayOf(2, 0, 0)
            ),
            Network(
                matI = simpleMatrixOf(
                    intArrayOf(1),
                    intArrayOf(0),
                ),
                matO = simpleMatrixOf(
                    intArrayOf(1),
                    intArrayOf(1)
                ),
                marking = intArrayOf(1, 0)
            )
        )

        val (matI, matO, marking) = networkList[1]

        val reachabilityTree = finder.find(
            matI = matI,
            matO = matO,
            initialMarking = marking.toDoubleArray()
        )

        val (markingMap, transitionMap) = reachabilityTree

        markingMap.values.forEach { item ->
            println("${item.name}. ${item.value.map { it.toInt() }}")
        }

        println("---")

        transitionMap.values.forEach { item ->
            println("${markingMap[item.source]?.name}->${item.name}->${markingMap[item.receiver]?.name}")
        }
    }
}