package ru.apetnet.pnutil.reachabilitytreefinder.helpers

import ru.apetnet.pnutil.reachabilitytreefinder.model.*
import ru.apetnet.pnutil.reachabilitytreefinder.util.ArcType
import ru.apetnet.pnutil.reachabilitytreefinder.util.VertexType
import java.util.*

/**
 * Stable (without omega-support)
 */
internal class VertexHelperImpl : VertexHelper {
    private val idList: MutableList<UUID> = mutableListOf()
    private val arcList: MutableList<Arc> = mutableListOf()

    private val vertexTypeMap: MutableMap<UUID, VertexType> = mutableMapOf()
    private val markingMap: MutableMap<UUID, ExtMarking> = mutableMapOf()
    private val markingSet: MutableSet<ExtMarking> = mutableSetOf()
    private val bindingSet: MutableSet<ExtBinding> = mutableSetOf()

    private val tMap: MutableMap<UUID, Int> = mutableMapOf()

    override fun addVertex(
        marking: DoubleArray,
        tId: Int
    ): Vertex {
        val id = UUID.randomUUID()
        val extMarking = buildMarking(
            marking
        )

        vertexTypeMap[id] = buildVertexType(extMarking)

        markingMap[id] = extMarking
        markingSet.add(extMarking)

        tMap[id] = tId
        idList.add(id)
        return getVertex(id)
    }

    override fun addArc(
        source: UUID,
        receiver: UUID,
        tId: Int
    ): Arc {
        val extBinding = ExtBinding(
            source = findNotDuplicateVertexId(source),
            receiver = findNotDuplicateVertexId(receiver),
            transitionIndex = tId
        )

        val arc = Arc.of(
            binding = extBinding,
            type = buildArcType(extBinding)
        )

        bindingSet.add(extBinding)
        arcList.add(arc)

        return arc
    }

    override fun setVertexType(
        vertex: Vertex,
        type: VertexType
    ) {
        vertexTypeMap[vertex.id] = type
    }

    override fun getResult(): ReachabilityTreeData {
        return ReachabilityTreeData(
            markingMap = collectMarkingMap(),
            transitionMap = collectTransitionMap()
        )
    }

    override fun getVertexList(): List<Vertex> {
        return idList.filter(::isNotDuplicate)
            .map(::getVertex)
    }

    override fun getArcList(): List<Arc> {
        return arcList.filter(::isNotDuplicate)
    }

    /*
     * Private
     * Collect
     */
    private fun collectMarkingMap(): Map<UUID, RtMarking> {
        val markingMap = mutableMapOf<UUID, RtMarking>()

        getVertexList().forEachIndexed { index, vertex ->
            markingMap[vertex.id] = RtMarking(
                id = index.toLong(),
                value = vertex.marking
            )
        }

        return markingMap
    }

    private fun collectTransitionMap(): Map<UUID, RtTransition> {
        val transitionMap = mutableMapOf<UUID, RtTransition>()

        getArcList().forEachIndexed { index, arc ->
            transitionMap[arc.id] = RtTransition(
                id = index,
                causedTransitionId = arc.transitionIndex,
                source = arc.source,
                receiver = arc.receiver
            )
        }

        return transitionMap
    }

    /*
     * Private
     * Find
     */
    private fun findNotDuplicateVertexId(id: UUID): UUID {
        if (isNotDuplicate(id)) {
            return id
        }
        else {
            val marking = markingMap[id]

            markingMap.forEach { (id, m) ->
                if (isNotDuplicate(id) && m == marking) {
                    return id
                }
            }
        }
        throw IllegalStateException()
    }

    /*
     * Private
     * Build
     */
    private fun buildMarking(
        marking: DoubleArray,
    ): ExtMarking {
        return ExtMarking(
            array = marking,
            isOmega = false
        )
    }

    private fun buildVertexType(
        marking: ExtMarking
    ): VertexType {
        return if (hasMarking(marking)) {
            VertexType.DUPLICATE
        } else {
            VertexType.BOUNDARY
        }
    }

    private fun buildArcType(
        binding: ExtBinding
    ): ArcType {
        return if (hasBinding(binding)) {
            ArcType.DUPLICATE
        } else {
            ArcType.ORDINARY
        }
    }

    private fun buildOmegaMarking(
        marking: DoubleArray,
        omegaList: List<Int>
    ): DoubleArray {
        val omegaMarking = marking.copyOf()

        omegaList.forEach {
            omegaMarking[it] = -1.0
        }

        return omegaMarking
    }

    /*
     * Private
     * Get
     */
    private fun getVertex(id: UUID): Vertex {
        val extMarking = checkNotNull(markingMap[id])
        return Vertex(
            id = id,
            type = requireNotNull(vertexTypeMap[id]),
            marking = extMarking.array,
            inputT = requireNotNull(tMap[id]),
            isOmega = extMarking.isOmega
        )
    }

    private fun getMarking(id: UUID): DoubleArray {
        return checkNotNull(markingMap[id]).array
    }

    /*
     * Private
     * Check
     */
    private fun hasMarking(marking: ExtMarking): Boolean {
        return markingSet.contains(marking)
    }

    private fun hasBinding(marking: ExtBinding): Boolean {
        return bindingSet.contains(marking)
    }

    private fun isNotDuplicate(id: UUID): Boolean {
        return vertexTypeMap[id] != VertexType.DUPLICATE
    }

    private fun isNotDuplicate(arc: Arc): Boolean {
        return arc.type != ArcType.DUPLICATE
    }
}