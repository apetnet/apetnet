package ru.apetnet.desktop.domain.ui.matrix

enum class KindIncidenceMatrices {
    IO,
    C;

    override fun toString(): String {
        return when (this) {
            IO -> "I/O"
            C -> "C"
        }
    }
}