package ru.apetnet.desktop.util.jundo

import com.gdetotut.jundo.UndoCommand
import com.gdetotut.jundo.UndoStack
import ru.apetnet.desktop.util.jundo.objects.WorkspaceUndoObject

sealed class WorkspaceUndoCommand<V>(
    protected open val owner: UndoStack,
    protected open val parent: UndoCommand?,
    protected open val old: V,
    protected open val new: V
) : UndoCommand(owner, "", parent) {
    val listener get() = owner.localContexts[UndoId.IDS_LISTENER] as? WorkspaceUndo

    data class AddObject(
        override val owner: UndoStack,
        override val parent: UndoCommand?,
        override val old: List<WorkspaceUndoObject>,
        override val new: List<WorkspaceUndoObject>
    ) : WorkspaceUndoCommand<List<WorkspaceUndoObject>>(
        owner = owner,
        parent = parent,
        old = old,
        new = new
    ) {
        override fun doRedo() {
            listener?.onListChanged(new)
        }

        override fun doUndo() {
            listener?.onListChanged(old)
        }
    }
}