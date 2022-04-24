package ru.apetnet.desktop.domain.ui.matrix.importmatrix

import javafx.geometry.Orientation

sealed class ImportMatrixOrientation(open val name: String) {
    data class Vertical(
        override val name: String
    ) : ImportMatrixOrientation(name) {
        override fun toString(): String {
            return name
        }
    }

    data class Horizontal(
        override val name: String
    ) : ImportMatrixOrientation(name) {
        override fun toString(): String {
            return name
        }
    }
}

fun ImportMatrixOrientation.toOrientation(): Orientation {
    return when (this) {
        is ImportMatrixOrientation.Vertical -> Orientation.VERTICAL
        is ImportMatrixOrientation.Horizontal -> Orientation.HORIZONTAL
    }
}