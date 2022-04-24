package ru.apetnet.desktop.ui.controller.matrix.importmatrix

import com.google.inject.Inject
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.ReadOnlyObjectProperty
import javafx.collections.ObservableList
import ru.apetnet.desktop.domain.service.importmatrix.ImportMatrixResult
import ru.apetnet.desktop.domain.ui.matrix.KindIncidenceMatrices
import ru.apetnet.desktop.domain.ui.matrix.MatrixRowState
import ru.apetnet.desktop.domain.ui.matrix.importmatrix.*
import ru.apetnet.desktop.domain.ui.parent.ControllerErrorData
import ru.apetnet.desktop.service.matrix.importmatrix.ImportMatrixService
import ru.apetnet.desktop.ui.controller.matrix.importmatrix.ImportMatrixController.Companion.MATRIX_SIZE
import ru.apetnet.desktop.ui.controller.matrix.importmatrix.ImportMatrixController.Companion.IMPORT_MATRIX_SOURCE
import ru.apetnet.desktop.ui.parent.ParentController
import tornadofx.*

class ImportMatrixControllerImpl @Inject constructor(
    private val service: ImportMatrixService
) : ParentController(), ImportMatrixController {
    override val positionNumberProperty: ObjectProperty<Int> = objectProperty(MATRIX_SIZE)
    override val transitionNumberProperty: ObjectProperty<Int> = objectProperty(MATRIX_SIZE)
    override val kindIncidenceMatricesProperty: ObjectProperty<KindIncidenceMatrices> =
        objectProperty(IMPORT_MATRIX_SOURCE)
    override val importMatrixOrientationProperty: ObjectProperty<ImportMatrixOrientation> = objectProperty(null)
    override val stateProperty: ObjectProperty<ImportMatrixState> = objectProperty(
        importMatrixStateOf(
            MATRIX_SIZE
        )
    )

    override val matrixResultProperty: ObjectProperty<ImportMatrixResult> = objectProperty()
    override val progressProperty: BooleanProperty get() = controllerProgressProperty

    override val errorProperty: ReadOnlyObjectProperty<ControllerErrorData>
        get() = controllerErrorProperty

    override val tableMatI: ObservableList<MatrixRowState> = observableListOf()
    override val tableMatO: ObservableList<MatrixRowState> = observableListOf()
    override val tableMatC: ObservableList<MatrixRowState> = observableListOf()
    override val tableMarking: ObservableList<MatrixRowState> = observableListOf()

    override val hasImportDataProperty: BooleanProperty = booleanProperty(false)

    private val positionNumber: Int get() = positionNumberProperty.value
    private val transitionNumber: Int get() = transitionNumberProperty.value
    private val kindIncidenceMatrices: KindIncidenceMatrices get() = kindIncidenceMatricesProperty.value
    private val importMatrixOrientation: ImportMatrixOrientation get() = importMatrixOrientationProperty.value

    private var prevPositionNumber: Int = -1
    private var prevTransitionNumber: Int = -1

    private lateinit var defaultMatrixOrientation: ImportMatrixOrientation

    override fun onPositionNumberChanged(number: Int) {
        if (prevPositionNumber != number) {
            refreshMatrix()
        }

        prevPositionNumber = number

        refreshCanImportProperty()
    }

    override fun onTransitionNumberChanged(number: Int) {
        if (prevTransitionNumber != number) {
            refreshMatrix()
        }
        prevTransitionNumber = number

        refreshCanImportProperty()
    }

    override fun onImportMatrixSourceChanged(source: KindIncidenceMatrices) {
        refreshMatrix()
        refreshCanImportProperty()
    }

    override fun setOrientation(orientation: ImportMatrixOrientation) {
        if (!::defaultMatrixOrientation.isInitialized) {
            defaultMatrixOrientation = orientation
        }
        importMatrixOrientationProperty.value = orientation
    }

    override fun onImportClicked() {
        startProgress()
        runAsync {
            when (kindIncidenceMatrices) {
                KindIncidenceMatrices.IO -> {
                    service.collectResultIO(
                        matI = tableMatI,
                        matO = tableMatO,
                        matMarking = tableMarking,
                        orientation = importMatrixOrientation.toOrientation()
                    )
                }
                KindIncidenceMatrices.C -> {
                    service.collectResultC(
                        matC = tableMatC,
                        matMarking = tableMarking,
                        orientation = importMatrixOrientation.toOrientation()
                    )
                }
            }
        } success {
            stopProgress()
            matrixResultProperty.value = it
        } fail {
            stopProgress()
            setError(it)
        }
    }

    override fun reset() {
        listOf(
            positionNumberProperty,
            transitionNumberProperty
        ).forEach {
            it.value = MATRIX_SIZE
        }

        listOf(
            tableMatI,
            tableMatO,
            tableMarking
        ).forEach {
            it.clear()
        }

        kindIncidenceMatricesProperty.value = IMPORT_MATRIX_SOURCE
        importMatrixOrientationProperty.value = defaultMatrixOrientation
    }

    private fun refreshMatrix() {
        startProgress()
        runAsync {
            collectMatrix()
        } ui { info ->
            stopProgress()
            setTableData(info)
            refreshState()
        }
    }

    private fun collectMatrix(): CollectMatrixInfo {
        val marking = collectMarking(
            colsNum = positionNumber
        )

        return when (kindIncidenceMatrices) {
            KindIncidenceMatrices.IO -> {
                CollectMatrixInfo.IO(
                    matI = collectMatrixI(
                        rowsNum = positionNumber,
                        colsNum = transitionNumber
                    ),
                    matO = collectMatrixO(
                        rowsNum = positionNumber,
                        colsNum = transitionNumber
                    ),
                    marking = marking
                )
            }
            KindIncidenceMatrices.C -> {
                CollectMatrixInfo.C(
                    matC = collectMatrixD(
                        rowsNum = positionNumber,
                        colsNum = transitionNumber
                    ),
                    marking = marking
                )

            }
        }
    }

    private fun setTableData(info: CollectMatrixInfo) {
        when (info) {
            is CollectMatrixInfo.C -> {
                tableMatC.setAll(info.matC)
            }
            is CollectMatrixInfo.IO -> {
                tableMatI.setAll(info.matI)
                tableMatO.setAll(info.matO)
            }
        }
        tableMarking.setAll(info.marking)
    }

    private fun collectMatrixI(
        rowsNum: Int,
        colsNum: Int
    ): List<MatrixRowState> {
        return service.collectMatrix(
            list = tableMatI,
            rowsNum = rowsNum,
            colsNum = colsNum
        )
    }

    private fun collectMatrixO(
        rowsNum: Int,
        colsNum: Int
    ): List<MatrixRowState> {
        return service.collectMatrix(
            list = tableMatO,
            rowsNum = rowsNum,
            colsNum = colsNum
        )
    }

    private fun collectMatrixD(
        rowsNum: Int,
        colsNum: Int
    ): List<MatrixRowState> {
        return service.collectMatrix(
            list = tableMatC,
            rowsNum = rowsNum,
            colsNum = colsNum
        )
    }

    private fun collectMarking(
        colsNum: Int
    ): List<MatrixRowState> {
        return service.collectMatrix(
            list = tableMarking,
            rowsNum = 1,
            colsNum = colsNum
        )
    }

    private fun refreshState() {
        stateProperty.value = ImportMatrixState(
            colNumMatrix = transitionNumber,
            colNumMarking = positionNumber,
            source = kindIncidenceMatrices,
            isValid = (positionNumber > 0 && transitionNumber > 0)
        )
    }

    private fun refreshCanImportProperty() {
        hasImportDataProperty.value = positionNumber > 0 && transitionNumber > 0
    }
}