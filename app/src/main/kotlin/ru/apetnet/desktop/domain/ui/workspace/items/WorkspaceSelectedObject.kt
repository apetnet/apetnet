package ru.apetnet.desktop.domain.ui.workspace.items

import java.util.*

data class WorkspaceSelectedObject(val id: UUID)

fun workspaceSelectedObjectOf(id: UUID): WorkspaceSelectedObject {
    return WorkspaceSelectedObject(id)
}