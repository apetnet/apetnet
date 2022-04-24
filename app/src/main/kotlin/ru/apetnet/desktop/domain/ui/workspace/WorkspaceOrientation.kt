package ru.apetnet.desktop.domain.ui.workspace

import javafx.geometry.Orientation

enum class WorkspaceOrientation {
    Horizontal,
    Vertical;

    fun toStringResource(): String {
        return when (this) {
            Horizontal -> "container.menu.view.orientation.horizontal"
            Vertical -> "container.menu.view.orientation.vertical"
        }
    }
}

fun WorkspaceOrientation.toOrientation(): Orientation {
    return when (this) {
        WorkspaceOrientation.Horizontal -> Orientation.HORIZONTAL
        WorkspaceOrientation.Vertical -> Orientation.VERTICAL
    }
}