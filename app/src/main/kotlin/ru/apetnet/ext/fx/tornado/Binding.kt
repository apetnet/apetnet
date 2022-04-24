package ru.apetnet.ext.fx.tornado

import javafx.beans.property.Property
import javafx.beans.value.ObservableValue
import tornadofx.select
import tornadofx.stringProperty

fun <T> ObservableValue<T>.selectString(nested: (T) -> String): Property<String> {
    return select { stringProperty(nested.invoke(it)) }
}