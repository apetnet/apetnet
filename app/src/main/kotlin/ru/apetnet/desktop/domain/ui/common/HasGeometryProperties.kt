package ru.apetnet.desktop.domain.ui.common

import javafx.geometry.Orientation
import javafx.geometry.Point2D
import ru.apetnet.ext.fx.java.geometry.Size2D

interface HasGeometryProperties {
    var centerPoint: Point2D
    val size: Size2D
    val orientation: Orientation
}