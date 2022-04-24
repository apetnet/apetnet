package ru.apetnet.desktop.domain.service.exportmatrix

import org.ejml.simple.SimpleMatrix

data class IncidenceMatrixResult(
    val matI: SimpleMatrix,
    val matO: SimpleMatrix,
    val matC: SimpleMatrix,
    val marking: SimpleMatrix,
)
