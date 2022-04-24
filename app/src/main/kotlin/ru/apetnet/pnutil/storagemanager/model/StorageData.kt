package ru.apetnet.pnutil.storagemanager.model

import java.util.*

data class StorageData(
    val version: Int,
    val id: UUID,
    val positions: List<StoragePositionData>,
    val transitions: List<StorageTransitionData>,
    val arcs: List<StorageArcData>
)