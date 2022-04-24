package ru.apetnet.desktop.util.log

import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState

fun safeLog(tag: String, lazyObj: () -> Any) {
    if (BuildConfig.DEBUG) {
        println("D/$tag: ${lazyObj.invoke()}")
    }
}