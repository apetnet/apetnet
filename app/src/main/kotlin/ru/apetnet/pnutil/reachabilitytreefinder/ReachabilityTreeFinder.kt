package ru.apetnet.pnutil.reachabilitytreefinder

import org.ejml.simple.SimpleMatrix
import ru.apetnet.pnutil.reachabilitytreefinder.model.ReachabilityTreeData

interface ReachabilityTreeFinder {
    fun find(
        matI: SimpleMatrix,
        matO: SimpleMatrix,
        initialMarking: DoubleArray
    ): ReachabilityTreeData

    companion object {
        const val TAG = "ReachabilityTreeFinder"
    }
}