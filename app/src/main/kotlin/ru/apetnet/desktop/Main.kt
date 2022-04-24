package ru.apetnet.desktop

import ru.apetnet.desktop.app.Application
import tornadofx.launch

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        launch<Application>(args)
    }
}