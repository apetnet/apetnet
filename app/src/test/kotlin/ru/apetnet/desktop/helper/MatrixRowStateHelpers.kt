package ru.apetnet.desktop.helper

import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import tornadofx.stringProperty

internal fun rowStateMatrixOf(vararg rows: IntArray): List<MatrixRowState> {
    val list = mutableListOf<MatrixRowState>()

    rows.forEachIndexed { id, row ->
        val values = row.map { stringProperty("$it") }
        val state = MatrixRowState(
            id = (id + BuildConfig.MATRIX_ROW_OFFSET).toString(),
            values = values
        )
        list.add(state)
    }

    return list
}