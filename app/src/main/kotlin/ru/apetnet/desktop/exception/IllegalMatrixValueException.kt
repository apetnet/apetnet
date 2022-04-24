package ru.apetnet.desktop.exception

class IllegalMatrixValueException(val value: Any) : Exception("Value: $value")