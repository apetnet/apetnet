package ru.apetnet.matutil.linearsolver.exception

class RootAlreadyExistsException(
    index: Int
) : Throwable("Root with index $index already exists!")