package ru.apetnet.ext.fx.tornado

import tornadofx.Fragment
import tornadofx.View
import java.util.*

fun View.string(id: String): String {
    return messages.getString(id)
}

fun Fragment.string(id: String): String {
    return messages.getString(id)
}

fun View.string(id: String, vararg any: Any?): String {
    return getString(
        bundle = messages,
        id = id,
        any = any
    )
}

fun Fragment.string(id: String, vararg any: Any?): String {
    return getString(
        bundle = messages,
        id = id,
        any = any
    )
}

private fun getString(bundle: ResourceBundle, id: String, vararg any: Any?): String {
    return if (any.isEmpty()) {
        bundle.getString(id)
    } else {
        java.lang.String.format(
            Locale.getDefault(),
            bundle.getString(id),
            *any
        )
    }
}