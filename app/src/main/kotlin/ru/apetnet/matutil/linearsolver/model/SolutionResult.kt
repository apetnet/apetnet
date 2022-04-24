package ru.apetnet.matutil.linearsolver.model

internal data class SolutionResult(
    val zeroIndices: List<Int>,
    val notEqualIndices: List<Int>,
    val equalIndices: List<Int>
)

data class EquationSolution(
    val solutionList: List<List<RootEquation>>
)

data class RootEquation(
    val index: Int,
    val value: Int
)