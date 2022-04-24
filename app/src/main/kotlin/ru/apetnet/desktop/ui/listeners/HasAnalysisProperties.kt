package ru.apetnet.desktop.ui.listeners

import javafx.beans.property.ReadOnlyObjectProperty
import ru.apetnet.desktop.domain.ui.workspace.analysis.WorkspaceAnalysisData

interface HasAnalysisProperties {
    val workspaceAnalysisProperty: ReadOnlyObjectProperty<WorkspaceAnalysisData>
}