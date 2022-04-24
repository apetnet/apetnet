package ru.apetnet.desktop.ui.listeners

import ru.apetnet.desktop.domain.ui.workspace.WorkspaceOrientation

interface HasWorkspaceOrientation {
    fun setWorkspaceOrientation(
        orientation: WorkspaceOrientation
    )
}