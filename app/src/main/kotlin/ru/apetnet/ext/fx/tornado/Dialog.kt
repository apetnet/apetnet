package ru.apetnet.ext.fx.tornado

import javafx.scene.control.TextInputDialog

fun fieldDialog(
    title: String = "",
    value: String = "",
    op: (value: String) -> Unit
): TextInputDialog = TextInputDialog(value).also { dialog ->
    dialog.title = title
    dialog.graphic = null
    dialog.headerText = null
    dialog.showAndWait().ifPresent {
        op.invoke(it)
    }
}
