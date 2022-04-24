package ru.apetnet.desktop.domain.ui.workspace.items

import ru.apetnet.ext.fx.java.scene.canvas.Renderable
import java.time.Instant

data class WorkspaceObjectState(
    val timestamp: Instant,
    val objects: List<Renderable>
)