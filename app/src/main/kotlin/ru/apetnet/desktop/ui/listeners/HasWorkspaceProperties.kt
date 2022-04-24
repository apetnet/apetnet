package ru.apetnet.desktop.ui.listeners

import javafx.beans.property.ReadOnlyBooleanProperty

interface HasWorkspaceProperties {
    val canUndoProperty: ReadOnlyBooleanProperty
    val canRedoProperty: ReadOnlyBooleanProperty
    val canSaveProperty: ReadOnlyBooleanProperty
}