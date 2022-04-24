package ru.apetnet.desktop.service.matrix

import javafx.geometry.Orientation
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.domain.service.matrixview.ImportedProjectInfo

interface MatrixViewService {
    fun importProject(
        matI: SimpleMatrix,
        matO: SimpleMatrix,
        matMarking: SimpleMatrix,
        positionNames: List<String>,
        transitionNames: List<String>,
        orientation: Orientation
    ): ImportedProjectInfo
}