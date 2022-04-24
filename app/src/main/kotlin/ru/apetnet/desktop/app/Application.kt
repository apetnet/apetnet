package ru.apetnet.desktop.app

import com.google.inject.Guice
import javafx.stage.Screen
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.di.ControllerModule
import ru.apetnet.desktop.di.ExternalModule
import ru.apetnet.desktop.di.ServiceModule
import ru.apetnet.desktop.ui.view.MainView
import ru.apetnet.ext.fx.java.util.ResourceBundleControl
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import java.util.*
import kotlin.reflect.KClass

class Application : App(MainView::class, Styles::class) {
    companion object {
        private val screen = Screen.getPrimary().bounds

        val TITLE
            get() = buildString {
                append(BuildConfig.NAME.capitalize())
                append(" v")
                append(BuildConfig.VERSION)
                append("-")
                append(
                    if (BuildConfig.DEBUG) {
                        "debug"
                    } else {
                        "release"
                    }
                )
                if(BuildConfig.DEBUG) {
                    append(" ")
                    append("(Scale Factor: ")
                    append(BuildConfig.SCALE_FACTOR)
                    append(";")
                    append(" ")
                    append("Scale: [")
                    append(BuildConfig.MIN_SCALE)
                    append(",")
                    append(" ")
                    append(BuildConfig.MAX_SCALE)
                    append("];")
                    append(" ")
                    append("Canvas Max Size: [")
                    append(BuildConfig.CANVAS_MAX_WIDTH)
                    append(",")
                    append(" ")
                    append(BuildConfig.CANVAS_MAX_HEIGHT)
                    append("])")
                }
            }

        val WINDOW_WIDTH get() = screen.width / 2
        val WINDOW_HEIGHT get() = screen.height / 2

        val MODAL_WIDTH get() = WINDOW_WIDTH / 2
        val MODAL_HEIGHT get() = WINDOW_HEIGHT / 2
    }

    override fun init() {
        val guice = Guice.createInjector(
            ServiceModule(),
            ControllerModule(),
            ExternalModule()
        )

        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>)
                    = guice.getInstance(type.java)
        }

        FX.messages = ResourceBundle.getBundle(
            "resource",
            ResourceBundleControl()
        )
    }
}