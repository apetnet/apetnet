package ru.apetnet.desktop.domain.ui.workspace

import java.io.File
import java.time.Instant
import java.util.*

data class WorkspaceProjectData(
    val id: UUID,
    val title: String,
    val file: File?,
    val instant: Instant
)