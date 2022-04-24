package ru.apetnet.matutil.linearsolver.exception

class DuplicateIndicesException(
    original: List<Int>,
    distinct: List<Int>
) : IllegalStateException("Duplicate indices at list of $original ($distinct)")