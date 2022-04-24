package ru.apetnet.desktop.model

import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief
import tornadofx.stringProperty

internal data class MockedPetriNet(
    val networkObject: String,
    val matI: List<List<Int>>,
    val matO: List<List<Int>>,
    val matC: List<List<Int>>,
    val matMarking: List<List<Int>>,
    val objects: List<WorkspaceObjectBrief>,
    val reachabilityTree: MockedReachabilityTreeResult,
    val murataSolution: MockedMurataSolution,
    val hasOmega: Boolean = false
) {
    val positionNames get() = objects.filterIsInstance<WorkspaceObjectBrief.Position>().map { it.name }
    val transitionNames = objects.filterIsInstance<WorkspaceObjectBrief.Transition>().map { it.name }
}

internal fun MockedPetriNet.toSimple(): MockedMatrixIO.Simple {
    return MockedMatrixIO.Simple(
        matI = matI.toSimpleMatrix(),
        matO = matO.toSimpleMatrix(),
        matC = matC.toSimpleMatrix(),
        matMarking = matMarking.toSimpleMatrix()
    )
}

internal fun MockedPetriNet.toRowState(): MockedMatrixIO.RowState {
    return MockedMatrixIO.RowState(
        matI = matI.toRowStateMatrix("P"),
        matO = matO.toRowStateMatrix("P"),
        matC = matC.toRowStateMatrix("P"),
        matMarking = matMarking.toRowStateMatrix("P")
    )
}

internal fun List<List<Any>>.toRowStateMatrix(prefix: String = "", isMarking: Boolean = false): List<MatrixRowState> {
    return mapIndexed { index, row ->
        val id = index + BuildConfig.MATRIX_ROW_OFFSET
        MatrixRowState(
            id = "$prefix$id", // Positions is vertically in the matrices (I, O)
            values = row.map {
                stringProperty(
                    if (it == -1 && isMarking) {
                        "ω"
                    } else {
                        "$it"
                    }
                )

            }
        )
    }
}

private fun List<List<Int>>.toSimpleMatrix(): SimpleMatrix {
    val array = map { row ->
        row.map {
            it.toDouble()
        }.toDoubleArray()
    }.toTypedArray()

    return SimpleMatrix(array)
}

