package ru.apetnet.desktop.domain.service.importmatrix

import javafx.geometry.Orientation
import org.ejml.simple.SimpleMatrix

data class ImportMatrixResult(
    val matI: SimpleMatrix,
    val matO: SimpleMatrix,
    val matMarking: SimpleMatrix,
    val positionNames: List<String>,
    val transitionNames: List<String>,
    val orientation: Orientation
)
