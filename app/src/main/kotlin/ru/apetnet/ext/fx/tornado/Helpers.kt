package ru.apetnet.ext.fx.tornado

import javafx.scene.input.KeyCombination
import javafx.stage.FileChooser

fun keyCombination(value: String?): KeyCombination {
    return KeyCombination.valueOf(value)
}

fun extensionFilter(
    description: String,
    vararg extensions: String
): FileChooser.ExtensionFilter {
    return FileChooser.ExtensionFilter(description, *extensions)
}