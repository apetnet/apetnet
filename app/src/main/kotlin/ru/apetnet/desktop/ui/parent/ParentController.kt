package ru.apetnet.desktop.ui.parent

import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyObjectProperty
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import tornadofx.Controller
import tornadofx.booleanProperty
import tornadofx.objectProperty
import java.time.Instant

abstract class ParentController : Controller() {
    protected val controllerErrorProperty: ObjectProperty<ControllerErrorData> = objectProperty()
    protected val controllerProgressProperty: BooleanProperty = booleanProperty(false)

    fun setError(throwable: Throwable) {
        controllerErrorProperty.value = ControllerErrorData(
            timestamp = Instant.now(),
            throwable = throwable
        )
    }

    fun startProgress() {
        controllerProgressProperty.value = true
    }

    fun stopProgress() {
        controllerProgressProperty.value = false
    }
}