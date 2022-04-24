package ru.apetnet.matutil.linearsolver.exception

import org.ejml.simple.SimpleMatrix

class VerifyException(
    val matrix: SimpleMatrix,
    val solution: String
) : Throwable()