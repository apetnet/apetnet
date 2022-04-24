package ru.apetnet.desktop.domain.ui.matrix.importmatrix

import ru.apetnet.desktop.domain.ui.matrix.KindIncidenceMatrices
import ru.apetnet.desktop.ui.controller.matrix.importmatrix.ImportMatrixController.Companion.IMPORT_MATRIX_SOURCE

data class ImportMatrixState(
    val colNumMatrix: Int,
    val colNumMarking: Int,
    val isValid: Boolean,
    val source: KindIncidenceMatrices
)

fun importMatrixStateOf(
    size: Int,
    source: KindIncidenceMatrices = IMPORT_MATRIX_SOURCE,
    isValid: Boolean = false
): ImportMatrixState {
    return ImportMatrixState(
        colNumMatrix = size,
        colNumMarking = size,
        source = source,
        isValid = isValid
    )
}