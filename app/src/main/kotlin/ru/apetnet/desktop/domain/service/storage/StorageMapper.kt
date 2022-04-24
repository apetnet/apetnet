package ru.apetnet.desktop.domain.service.storage

import javafx.geometry.Orientation
import ru.apetnet.desktop.domain.ui.workspace.items.ArcReceiver
import ru.apetnet.desktop.domain.ui.workspace.items.ArcSource
import ru.apetnet.desktop.domain.ui.workspace.items.ArcType
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import ru.apetnet.pnutil.storagemanager.StorageManager
import ru.apetnet.pnutil.storagemanager.model.*
import java.util.*

fun mapToWorkspaceObject(data: StorageData): ProjectData<WorkspaceObject> {
    val positionList = data.positions.map { it.toWorkspaceObject() }
    val transitionList = data.transitions.map { it.toWorkspaceObject() }
    val arcList = data.arcs.map {
        it.toWorkspaceObject()
    }
    return ProjectData(
        id = data.id,
        items = (positionList + transitionList + arcList)
    )
}

fun mapToStorageData(values: List<WorkspaceObject>, id: UUID): StorageData {
    val positions = values.filterIsInstance<WorkspaceObject.Position>()
    val transitions = values.filterIsInstance<WorkspaceObject.Transition>()
    val arcs = values.filterIsInstance<WorkspaceObject.Arc>()

    return StorageData(
        version = StorageManager.VERSION,
        id = id,
        positions = positions.map { it.toStorage() },
        transitions = transitions.map { it.toStorage() },
        arcs = arcs.mapNotNull { it.toStorage() }
    )
}

// WorkspaceObject to Storage
private fun WorkspaceObject.Position.toStorage(): StoragePositionData {
    return StoragePositionData(
        id = id,
        name = name,
        description = description,
        tokensNumber = tokensNumber,
        center = centerPoint,
        size = size,
        orientation = orientation.toStorage()
    )
}

private fun WorkspaceObject.Transition.toStorage(): StorageTransitionData {
    return StorageTransitionData(
        id = id,
        name = name,
        description = description,
        center = centerPoint,
        size = size,
        orientation = orientation.toStorage()
    )
}

private fun WorkspaceObject.Arc.toStorage(): StorageArcData? {
    val receiver = receiver.toStorage()

    return if (receiver != null) {
        StorageArcData(
            id = id,
            name = name,
            description = description,
            source = source.toStorage(),
            points = points,
            receiver = receiver,
            arcType = type.toStorage()
        )
    } else {
        null
    }
}

// Storage to WorkspaceObject
private fun StoragePositionData.toWorkspaceObject(): WorkspaceObject.Position {
    return WorkspaceObject.Position(
        id = id,
        name = name,
        description = description,
        tokensNumber = tokensNumber,
        centerPoint = center,
        size = size,
        orientation = orientation.toWorkspace()
    )
}

private fun StorageTransitionData.toWorkspaceObject(): WorkspaceObject.Transition {
    return WorkspaceObject.Transition(
        id = id,
        name = name,
        description = description,
        centerPoint = center,
        size = size,
        orientation = orientation.toWorkspace()
    )
}

private fun StorageArcData.toWorkspaceObject(): WorkspaceObject.Arc {
    return WorkspaceObject.Arc(
        id = id,
        name = name,
        description = description,
        source = source.toWorkspace(),
        points = points,
        receiver = receiver.toWorkspace(),
        type = arcType.toWorkspace()
    )
}

// Mapper toStorage
private fun ArcType.toStorage(): StorageArcType {
    return when (this) {
        ArcType.FROM_POSITION -> StorageArcType.P2T
        ArcType.FROM_TRANSITION -> StorageArcType.T2P
    }
}

private fun ArcSource.toStorage(): StorageArcSource {
    return StorageArcSource(id)
}

private fun ArcReceiver.toStorage(): StorageArcReceiver? {
    return when (this) {
        is ArcReceiver.Point -> null
        is ArcReceiver.Uuid -> StorageArcReceiver(uuid)
    }
}

private fun Orientation.toStorage(): StorageOrientation {
    return when (this) {
        Orientation.HORIZONTAL -> StorageOrientation.HORIZONTAL
        Orientation.VERTICAL -> StorageOrientation.VERTICAL
    }
}

// Mapper toWorkspace
private fun StorageArcType.toWorkspace(): ArcType {
    return when (this) {
        StorageArcType.P2T -> ArcType.FROM_POSITION
        StorageArcType.T2P -> ArcType.FROM_TRANSITION
    }
}

private fun StorageArcSource.toWorkspace(): ArcSource {
    return ArcSource(id)
}

private fun StorageArcReceiver.toWorkspace(): ArcReceiver {
    return ArcReceiver.Uuid(uuid)
}

private fun StorageOrientation.toWorkspace(): Orientation {
    return when (this) {
        StorageOrientation.HORIZONTAL -> Orientation.HORIZONTAL
        StorageOrientation.VERTICAL -> Orientation.VERTICAL
    }
}