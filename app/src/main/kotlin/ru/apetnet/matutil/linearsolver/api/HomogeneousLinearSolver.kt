package ru.apetnet.matutil.linearsolver.api

import org.ejml.simple.SimpleMatrix
import ru.apetnet.matutil.linearsolver.model.EquationSolution

interface HomogeneousLinearSolver {
    fun solve(
        matrix: SimpleMatrix
    ): EquationSolution
}