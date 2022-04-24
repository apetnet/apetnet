package ru.apetnet.matutil.linearsolver.exception

class IsNotMathematicalIntegerException(
    value: Double
) : IllegalStateException("$value is not mathematical integer!")