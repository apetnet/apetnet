package ru.apetnet.ext.fx.java.util

import java.util.*


class ResourceBundleControl : ResourceBundle.Control() {
    override fun getCandidateLocales(baseName: String?, locale: Locale?): MutableList<Locale> {
        return mutableListOf(
            if (locale != null && locale.language.contains("ru", ignoreCase = true)) {
                Locale("ru")
            }
            else {
                Locale("en")
            }
        )
    }
}