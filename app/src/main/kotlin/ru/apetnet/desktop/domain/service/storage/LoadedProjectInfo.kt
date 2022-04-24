package ru.apetnet.desktop.domain.service.storage

import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import java.io.File
import java.util.*

data class LoadedProjectInfo(
    val id: UUID,
    val file: File,
    val items: List<WorkspaceObject>
)