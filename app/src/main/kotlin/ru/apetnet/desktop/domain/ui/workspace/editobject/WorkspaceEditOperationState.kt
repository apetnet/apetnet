package ru.apetnet.desktop.domain.ui.workspace.editobject

import ru.apetnet.desktop.exception.PredefinedErrorException
import java.util.*

sealed class WorkspaceEditOperationState {
    data class SetToken(
        val id: UUID,
        val tokenNumber: Int
    ) : WorkspaceEditOperationState()

    data class Rename(
        val id: UUID,
        val name: String
    ) : WorkspaceEditOperationState()

    data class Description(
        val id: UUID,
        val description: String
    ) : WorkspaceEditOperationState()

    data class Error(
        val exception: PredefinedErrorException
    ) : WorkspaceEditOperationState()
}

enum class WorkspaceEditOperation {
    SetToken,
    Rename
}

fun mapToWorkspaceEditOperationState(
    objectState: WorkspaceEditObjectState,
    operation: WorkspaceEditOperation
): WorkspaceEditOperationState {
    return when {
        operation == WorkspaceEditOperation.SetToken && objectState is WorkspaceEditObjectState.Position -> {
            WorkspaceEditOperationState.SetToken(
                id = objectState.id,
                tokenNumber = objectState.tokenNumber
            )
        }
        operation == WorkspaceEditOperation.Rename -> {
            WorkspaceEditOperationState.Rename(
                id = objectState.id,
                name = objectState.name
            )
        }
        else -> {
            WorkspaceEditOperationState.Error(
                PredefinedErrorException(
                    errorId = "container.error.edit_object_error"
                )
            )
        }
    }
}