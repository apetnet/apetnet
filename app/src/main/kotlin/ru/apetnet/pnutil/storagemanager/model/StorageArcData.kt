package ru.apetnet.pnutil.storagemanager.model

import javafx.geometry.Point2D
import java.util.*

data class StorageArcData(
    val id: UUID,
    val name: String,
    val description: String,
    val source: StorageArcSource,
    val points: List<Point2D>,
    val receiver: StorageArcReceiver,
    val arcType: StorageArcType
)

enum class StorageArcType {
    P2T,
    T2P
}

data class StorageArcSource(
    val id: UUID
)

data class StorageArcReceiver(
    val uuid: UUID
)