package ru.apetnet.pnutil.reachabilitytreefinder.model

import ru.apetnet.pnutil.reachabilitytreefinder.util.ArcType
import java.util.*

internal data class Arc(
    val id: UUID = UUID.randomUUID(),
    val type: ArcType,
    val source: UUID,
    val receiver: UUID,
    val transitionIndex: Int
) {
    companion object {
        fun of(
            binding: ExtBinding,
            type: ArcType
        ): Arc {
            return Arc(
                type = type,
                source = binding.source,
                receiver = binding.receiver,
                transitionIndex = binding.transitionIndex
            )
        }
    }
}