package ru.apetnet.desktop.domain.ui.main

enum class WorkspaceTool {
    AddPosition,
    AddTransition,
    AddArc,
    SetToken,
    Rename,
    Delete,
    Move,
    Cursor,
    Rotate;

    fun toStringResource(): String {
        return when (this) {
            AddPosition -> "workspace.tool.add_position"
            AddTransition -> "workspace.tool.add_transition"
            Delete -> "workspace.tool.delete"
            Move -> "workspace.tool.move"
            AddArc -> "workspace.tool.add_arc"
            SetToken -> "workspace.tab.fragment.context_menu.set_token"
            Rename -> "workspace.tab.fragment.context_menu.rename"
            Cursor -> "workspace.tool.cursor"
            Rotate -> "workspace.tool.rotate"
        }
    }
}