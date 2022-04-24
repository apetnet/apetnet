package ru.apetnet.desktop.domain.ui.main

import ru.apetnet.desktop.domain.ui.workspace.WorkspaceOrientation

data class MainMenuOrientationItem(
    override val nameId: String,
    override val keyCombination: String,
    val orientation: WorkspaceOrientation
) : MainMenuCheckItem