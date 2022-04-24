package ru.apetnet.desktop.util.resource

import ru.apetnet.ext.fx.tornado.string
import tornadofx.Fragment
import tornadofx.View

fun View.string(id: String, vararg any: Any?): String {
    return string(
        id = id,
        any = any
    )
}

fun Fragment.string(id: String, vararg any: Any?): String {
    return string(
        id = id,
        any = any
    )
}
