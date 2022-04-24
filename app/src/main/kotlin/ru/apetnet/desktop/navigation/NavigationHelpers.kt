package ru.apetnet.desktop.navigation

import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.Fragment
import tornadofx.UIComponent
import tornadofx.find
import tornadofx.tab
const val ARGS = "args"

inline fun <reified T : UIComponent> TabPane.tab(arguments: NavArguments, noinline op: Tab.() -> Unit = {}): Tab {
    return tab(
        uiComponent = find(
            type = T::class,
            params = mapOf(ARGS to arguments)
        )
    ) {
        op.invoke(this)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : NavArguments> Fragment.requireArguments(): T {
    return params[ARGS] as T
}