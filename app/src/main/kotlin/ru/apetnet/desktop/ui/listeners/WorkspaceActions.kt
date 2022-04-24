package ru.apetnet.desktop.ui.listeners

import ru.apetnet.ext.fx.java.scene.canvas.ScaleOperation
import java.io.File

interface WorkspaceActions {
    fun onUndoClicked() = Unit
    fun onRedoClicked() = Unit
    fun onCleanClicked() = Unit
    fun onCloseClicked() = Unit
    fun onSaveClicked() = Unit
    fun onSaveAsClicked(file: File) = Unit
    fun onScaleChanged(scale: ScaleOperation) = Unit
}