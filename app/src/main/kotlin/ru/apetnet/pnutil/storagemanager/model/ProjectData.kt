package ru.apetnet.pnutil.storagemanager.model

import java.util.*

data class ProjectData<T>(
    val id: UUID,
    val items: List<T>
)