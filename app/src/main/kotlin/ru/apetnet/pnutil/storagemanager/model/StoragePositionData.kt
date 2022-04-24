package ru.apetnet.pnutil.storagemanager.model

import javafx.geometry.Point2D
import ru.apetnet.ext.fx.java.geometry.Size2D
import java.math.BigInteger
import java.util.*

data class StoragePositionData(
    val id: UUID,
    val name: String,
    val description: String,
    val tokensNumber: Int,
    val center: Point2D,
    val size: Size2D,
    val orientation: StorageOrientation
)