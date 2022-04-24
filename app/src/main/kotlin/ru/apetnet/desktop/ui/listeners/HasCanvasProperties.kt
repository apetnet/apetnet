package ru.apetnet.desktop.ui.listeners

import javafx.beans.property.ReadOnlyObjectProperty
import javafx.geometry.Point2D
import ru.apetnet.ext.fx.java.geometry.Scale

interface HasCanvasProperties {
    val mousePositionProperty: ReadOnlyObjectProperty<Point2D>
    val scaleProperty: ReadOnlyObjectProperty<Scale>
}