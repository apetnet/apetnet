package ru.apetnet.pnutil.reachabilitytreefinder.impl

import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.util.log.safeLog
import ru.apetnet.pnutil.extension.*
import ru.apetnet.pnutil.reachabilitytreefinder.ReachabilityTreeFinder
import ru.apetnet.pnutil.reachabilitytreefinder.ReachabilityTreeFinder.Companion.TAG
import ru.apetnet.pnutil.reachabilitytreefinder.helpers.VertexHelper
import ru.apetnet.pnutil.reachabilitytreefinder.helpers.VertexHelperImpl
import ru.apetnet.pnutil.reachabilitytreefinder.model.ReachabilityTreeData
import ru.apetnet.pnutil.reachabilitytreefinder.model.Vertex
import ru.apetnet.pnutil.reachabilitytreefinder.util.VertexType

class ReachabilityTreeFinderImpl : ReachabilityTreeFinder {
    private lateinit var helper: VertexHelper

    override fun find(
        matI: SimpleMatrix,
        matO: SimpleMatrix,
        initialMarking: DoubleArray
    ): ReachabilityTreeData {
        helper = VertexHelperImpl()

        val vertex = helper.addVertex(
            marking = initialMarking,
            tId = -1
        )

        processVertex(
            vertex = vertex,
            matI = matI,
            matO = matO
        )

        if (BuildConfig.DEBUG) {
            var index = 1
            helper.getVertexList().forEach { v ->
                safeLog(TAG) {
                    "$index. ${v.type}, marking: ${v.marking.toList()}, T${v.inputT + 1}"
                }
                index++
            }
        }

        return helper.getResult()
    }

    private fun processVertex(
        vertex: Vertex,
        matI: SimpleMatrix,
        matO: SimpleMatrix
    ) {
        val vertexMarking = vertex.marking

        if (vertex.type == VertexType.BOUNDARY) {
            var isTerminal = true

            vertexMarking.findAllP().forEach { markingPId -> // markingPId - нахожу в каких позициях есть метки
                matI.postP(markingPId).forEach { tId -> // нахожу в какие переходы есть дуги
                    val newVertexMarking = vertexMarking.copyOf()
                    val preT = matI.preT(tId)
                    val postT = matO.postT(tId)

                    if (preT.count { vertexMarking[it] > 0 } == preT.size && postT.isNotEmpty()) {
                        preT.forEach(newVertexMarking::removeToken)
                        postT.forEach(newVertexMarking::addToken)

                        val newVertex = helper.addVertex(
                            marking = newVertexMarking,
                            tId = tId
                        )

                        helper.addArc(
                            source = vertex.id,
                            receiver = newVertex.id,
                            tId = tId
                        )

                        safeLog(TAG) {
                            "From P(${preT.map { it + 1 }}) to P(${postT.map { it + 1 }}) via T${tId + 1} by marking ${vertexMarking.toList()} = ${newVertex.marking.toList()}"
                        }

                        processVertex(
                            matI = matI,
                            matO = matO,
                            vertex = newVertex
                        )

                        isTerminal = false
                    }
                }
            }

            updateVertexType(
                vertex = vertex,
                isTerminal = isTerminal
            )
        }
    }

    private fun updateVertexType(
        vertex: Vertex,
        isTerminal: Boolean
    ) {
        helper.setVertexType(
            vertex = vertex,
            type = if (isTerminal) {
                VertexType.TERMINAL
            } else {
                VertexType.INTERNAL
            }
        )
    }
}
