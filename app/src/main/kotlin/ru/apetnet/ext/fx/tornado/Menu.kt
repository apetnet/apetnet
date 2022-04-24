package ru.apetnet.ext.fx.tornado

import javafx.beans.property.Property
import javafx.beans.value.ObservableValue
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import tornadofx.action
import tornadofx.checkmenuitem
import tornadofx.enableWhen
import tornadofx.item

fun Menu.menuitem(
    name: String,
    keyCombination: String? = null,
    enableWhen: (() -> ObservableValue<Boolean>)? = null,
    action: () -> Unit = {},
): MenuItem {
    return item(
        name = name,
        keyCombination = keyCombination?.let {
            keyCombination(it)
        }
    ) {
        action {
            action.invoke()
        }
        if (enableWhen != null) {
            enableWhen { enableWhen.invoke() }
        }
    }
}

fun Menu.menucheckitem(
    name: String,
    keyCombination: String? = null,
    enableWhen: (() -> ObservableValue<Boolean>)? = null,
    selected: Property<Boolean>? = null,
    action: () -> Unit = {},
): MenuItem {
    return checkmenuitem(
        name = name,
        keyCombination = keyCombination?.let {
            keyCombination(it)
        },
        selected = selected
    ) {
        action {
            action.invoke()
        }
        if (enableWhen != null) {
            enableWhen { enableWhen.invoke() }
        }
    }
}