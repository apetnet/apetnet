package ru.apetnet.desktop.ui.listeners

import ru.apetnet.desktop.domain.ui.main.WorkspaceTool

interface HasWorkspaceTool {
    fun setWorkspaceTool(tool: WorkspaceTool)
}