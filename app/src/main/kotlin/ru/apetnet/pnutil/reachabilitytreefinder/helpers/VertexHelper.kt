package ru.apetnet.pnutil.reachabilitytreefinder.helpers

import ru.apetnet.pnutil.reachabilitytreefinder.model.Arc
import ru.apetnet.pnutil.reachabilitytreefinder.model.ReachabilityTreeData
import ru.apetnet.pnutil.reachabilitytreefinder.model.Vertex
import ru.apetnet.pnutil.reachabilitytreefinder.util.VertexType
import java.util.*

internal interface VertexHelper {
    fun addVertex(
        marking: DoubleArray,
        tId: Int
    ): Vertex

    fun addArc(
        source: UUID,
        receiver: UUID,
        tId: Int
    ): Arc

    fun setVertexType(
        vertex: Vertex,
        type: VertexType
    )

    fun getResult(): ReachabilityTreeData

    fun getVertexList(): List<Vertex>

    fun getArcList(): List<Arc>
}