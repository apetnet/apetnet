package ru.apetnet.ext.fx.petrinet.scene.canvas.parent

import javafx.geometry.Point2D
import javafx.geometry.Rectangle2D
import ru.apetnet.ext.fx.java.geometry.Size2D
import ru.apetnet.ext.fx.java.scene.canvas.Renderable

interface PnObject : Renderable {
    val x: Double
    val y: Double
    val width: Double
    val height: Double

    val boundary: Rectangle2D
    val point: Point2D
    val center: Point2D
    val size: Size2D

    val name: String
    val description: String
}