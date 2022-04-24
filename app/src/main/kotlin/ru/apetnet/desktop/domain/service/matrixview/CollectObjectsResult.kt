package ru.apetnet.desktop.domain.service.matrixview

import javafx.geometry.Point2D
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject

data class CollectObjectsResult<T : WorkspaceObject>(
    val nextPoint: Point2D,
    val list: List<T>
)