package ru.apetnet.desktop.exception

class UnsupportedExtensionException(
    extension: String
) : Exception("Unsupported extension: $extension")