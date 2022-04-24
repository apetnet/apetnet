package ru.apetnet.desktop.service.matrix.exportmatrix

import com.google.inject.Inject
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.domain.service.exportmatrix.ExportMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import ru.apetnet.desktop.domain.ui.matrix.toMatrixRowStateList
import ru.apetnet.desktop.service.matrix.helpers.MatrixHelperService
import ru.apetnet.ext.ejml.forEachRowIndexed
import tornadofx.stringProperty

class ExportMatrixServiceImpl @Inject constructor(
    private val matrixHelper: MatrixHelperService
) : ExportMatrixService {
    override fun collectExportMatrix(
        items: List<WorkspaceObjectBrief>
    ): ExportMatrixResult {
        val arcs = items.filterIsInstance<WorkspaceObjectBrief.Arc>()
        val positions = items.filterIsInstance<WorkspaceObjectBrief.Position>()
        val transitions = items.filterIsInstance<WorkspaceObjectBrief.Transition>()

        val matrices = matrixHelper.collectIncidenceMatrix(
            items = items,
            positions = positions,
            transitions = transitions,
            arcs = arcs
        )

        return ExportMatrixResult(
            matI = collectMatrixRowStates(
                matrix = matrices.matI,
                rowObjects = positions
            ),
            matO = collectMatrixRowStates(
                matrix = matrices.matO,
                rowObjects = positions
            ),
            matC = collectMatrixRowStates(
                matrix = matrices.matC,
                rowObjects = positions
            ),
            marking = collectMatrixRowStates(
                matrix = matrices.marking,
                rowObjects = positions
            ),
            positionNames = positions.map { it.name },
            transitionNames = transitions.map { it.name },
        )
    }

    private fun collectMatrixRowStates(
        matrix: SimpleMatrix,
        rowObjects: List<WorkspaceObjectBrief>
    ): List<MatrixRowState> {
        return matrix.toMatrixRowStateList { rowObjects[it].name }
    }


}