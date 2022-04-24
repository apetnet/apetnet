package ru.apetnet.desktop.ui.listeners

import javafx.beans.property.ReadOnlyObjectProperty
import ru.apetnet.desktop.domain.ui.main.WorkspaceTool

interface HasSelectedToolProperty {
    val selectedToolProperty: ReadOnlyObjectProperty<WorkspaceTool>
}