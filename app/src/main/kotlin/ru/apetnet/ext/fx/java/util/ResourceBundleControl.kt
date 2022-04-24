package ru.apetnet.ext.fx.java.util

import java.util.*


class ResourceBundleControl : ResourceBundle.Control() {
    override fun getCandidateLocales(baseName: String?, locale: Locale?): MutableList<Locale> {
        return mutableListOf(
            Locale("ru"),
            Locale("en")
        )
    }
}