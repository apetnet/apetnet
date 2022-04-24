package ru.apetnet.desktop.domain.service.workspace

import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import java.time.Instant
import java.util.*

data class WorkspaceObjectModel(
    val timestamp: Instant,
    val objects: Map<UUID, WorkspaceObject>
)