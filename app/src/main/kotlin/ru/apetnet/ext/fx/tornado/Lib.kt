package ru.apetnet.ext.fx.tornado

import javafx.beans.value.ObservableValue
import tornadofx.onChange

fun <T> ObservableValue<T>.onNotNullChange(op: (T) -> Unit) = onChange {
    it?.let(op)
}

fun <T> ObservableValue<T>.onChangeDetail(op: (oldValue: T?, newValue: T?) -> Unit) = apply { addListener { o, oldValue, newValue -> op(oldValue, newValue) } }