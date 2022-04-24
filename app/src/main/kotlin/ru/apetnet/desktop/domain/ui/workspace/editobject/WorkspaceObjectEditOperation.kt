package ru.apetnet.desktop.domain.ui.workspace.editobject

import java.math.BigInteger
import java.util.*

sealed class WorkspaceObjectEditOperation(
    open val id: UUID
) {
    data class Delete(
        override val id: UUID
    ) : WorkspaceObjectEditOperation(id)

    data class SetToken(
        override val id: UUID,
        val tokenNumber: Int
    ) : WorkspaceObjectEditOperation(id)

    data class Rename(
        override val id: UUID,
        val name: String
    ) : WorkspaceObjectEditOperation(id)

    data class Description(
        override val id: UUID,
        val description: String
    ) : WorkspaceObjectEditOperation(id)

    data class Rotate(
        override val id: UUID
    ) : WorkspaceObjectEditOperation(id)
}