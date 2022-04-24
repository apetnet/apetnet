package ru.apetnet.desktop.domain.ui.main

sealed class MainMenuActionItem {
    data class Tool(
        override val nameId: String,
        override val keyCombination: String,
        val tool: WorkspaceTool
    ) : MainMenuActionItem(), MainMenuCheckItem

    object Separator : MainMenuActionItem()
}


