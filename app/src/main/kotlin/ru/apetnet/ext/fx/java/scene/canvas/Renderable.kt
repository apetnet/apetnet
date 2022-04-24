package ru.apetnet.ext.fx.java.scene.canvas

import javafx.scene.canvas.GraphicsContext

interface Renderable {
    fun render(gc: GraphicsContext)
}