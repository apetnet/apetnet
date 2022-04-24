package ru.apetnet.desktop.util.jundo

import ru.apetnet.desktop.util.jundo.objects.WorkspaceUndoObject

interface WorkspaceUndo {
    fun onListChanged(list: List<WorkspaceUndoObject>)
}