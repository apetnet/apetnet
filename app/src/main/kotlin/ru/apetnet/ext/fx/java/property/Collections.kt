package ru.apetnet.ext.fx.java.property

import javafx.beans.property.ObjectProperty

@Deprecated("♿ (следует избавиться)")
fun <T> ObjectProperty<List<T>>.add(workspaceObject: T) {
    val items = (value ?: listOf()).toMutableList()
    items.add(workspaceObject)
    value = items
}