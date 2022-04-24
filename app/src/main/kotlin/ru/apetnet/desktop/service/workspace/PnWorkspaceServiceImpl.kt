package ru.apetnet.desktop.service.workspace

import com.google.inject.Inject
import javafx.beans.property.ObjectProperty
import javafx.geometry.Orientation
import javafx.geometry.Point2D
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.domain.service.storage.SavedProjectInfo
import ru.apetnet.desktop.domain.service.workspace.WorkspaceObjectModel
import ru.apetnet.desktop.domain.ui.matrix.toBrief
import ru.apetnet.desktop.domain.ui.workspace.editobject.WorkspaceEditObjectState
import ru.apetnet.desktop.domain.ui.workspace.items.*
import ru.apetnet.desktop.domain.ui.workspace.matrix.WorkspaceMatrixInputData
import ru.apetnet.desktop.exception.PredefinedErrorException
import ru.apetnet.desktop.service.storage.StorageService
import ru.apetnet.desktop.util.jundo.WorkspaceUndo
import ru.apetnet.desktop.util.jundo.objects.WorkspaceUndoObject
import ru.apetnet.desktop.util.jundo.objects.toObject
import ru.apetnet.desktop.util.jundo.objects.toUndoObject
import ru.apetnet.desktop.util.log.safeLog
import ru.apetnet.pnutil.storagemanager.model.ProjectData
import tornadofx.objectProperty
import java.io.File
import java.time.Instant
import java.util.*

class PnWorkspaceServiceImpl @Inject constructor(
    private val storageService: StorageService
) : PnWorkspaceService, WorkspaceUndo {
    companion object {
        const val TAG = "PnWorkspaceServiceImpl"
    }

    override val workspaceUndoObjectMapProperty: ObjectProperty<List<WorkspaceUndoObject>> = objectProperty()

    override val workspaceObjectModel: WorkspaceObjectModel
        get() = WorkspaceObjectModel(
            timestamp = lastUpdateTimestamp,
            objects = objectMap
        )

    private val objectMap: MutableMap<UUID, WorkspaceObject> = mutableMapOf()

    private var lastUpdateTimestamp: Instant = Instant.EPOCH

    override fun addPosition(
        point: Point2D,
        orientation: Orientation
    ) {
        val newPosition = workspacePositionOf(
            index = objectMap.count { it.value is WorkspaceObject.Position }.toLong(),
            point = point,
            orientation = orientation
        )

        putToObjectMap(newPosition)
    }

    override fun addTransition(
        point: Point2D,
        orientation: Orientation
    ) {
        val newTransition = workspaceTransitionOf(
            index = objectMap.count { it.value is WorkspaceObject.Transition }.toLong(),
            point = point,
            orientation = orientation
        )
        putToObjectMap(newTransition)
    }

    override fun addArc(point: Point2D) {
        val last = findLastArc()
        val obj = findContainsObject(point)

        val arc = when {
            last != null && last.receiver is ArcReceiver.Point -> {
                val isFromPosition = last.type == ArcType.FROM_POSITION && obj is WorkspaceObject.Transition
                val isFromTransition = last.type == ArcType.FROM_TRANSITION && obj is WorkspaceObject.Position

                if (obj != null && (isFromPosition || isFromTransition)) {
                    last.copy(
                        receiver = ArcReceiver.Uuid(obj.id)
                    )
                } else {
                    val points = last.points.toMutableList()
                    points.add(point)

                    last.copy(
                        points = points
                    )
                }
            }
            obj != null -> {
                workspaceArcOf(
                    index = objectMap.count { it.value is WorkspaceObject.Arc }.toLong(),
                    source = ArcSource(obj.id),
                    receiver = ArcReceiver.Point(point),
                    type = if (obj is WorkspaceObject.Position) {
                        ArcType.FROM_POSITION
                    } else {
                        ArcType.FROM_TRANSITION
                    }
                )
            }
            else -> null
        }

        if (arc != null) {
            putToObjectMap(arc)
        }
    }

    override fun setArcDirectionPoint(point: Point2D) {
        val last = findLastArc()
        if (last != null && last.receiver is ArcReceiver.Point) {
            putToObjectMap(
                last.copy(receiver = ArcReceiver.Point(point))
            )
        }
    }

    override fun moveObject(id: UUID, point: Point2D) {
        val newObject = when (val obj = objectMap[id]) {
            is WorkspaceObject.Position -> {
                obj.copy(
                    centerPoint = point,
                    isMoving = true
                )
            }
            is WorkspaceObject.Transition -> {
                obj.copy(
                    centerPoint = point,
                    isMoving = true
                )
            }
            else -> null
        }

        if (newObject != null) {
            putToObjectMap(newObject)
        }
    }

    override fun stopObjectMoving(id: UUID) {
        val obj = when (val obj = objectMap[id]) {
            is WorkspaceObject.Position -> {
                obj.copy(
                    isMoving = false
                )
            }
            is WorkspaceObject.Transition -> {
                obj.copy(
                    isMoving = false
                )
            }
            else -> null
        }

        if (obj != null) {
            putToObjectMap(obj)
        }
    }

    override fun setTokenNumber(id: UUID, number: Int) {
        val obj = objectMap[id] as? WorkspaceObject.Position
        if (obj != null) {
            putToObjectMap(
                obj.copy(
                    tokensNumber = number
                )
            )
        }
    }

    override fun setObjectName(id: UUID, name: String) {
        val obj = when (val obj = objectMap[id]) {
            is WorkspaceObject.Position -> {
                obj.copy(
                    name = name
                )
            }
            is WorkspaceObject.Transition -> {
                obj.copy(
                    name = name
                )
            }
            else -> null
        }

        if (obj != null) {
            putToObjectMap(obj)
        }
    }

    override fun setObjectDescription(id: UUID, description: String) {
        val obj = when (val obj = objectMap[id]) {
            is WorkspaceObject.Position -> {
                obj.copy(
                    description = description
                )
            }
            is WorkspaceObject.Transition -> {
                obj.copy(
                    description = description
                )
            }
            else -> null
        }

        if (obj != null) {
            putToObjectMap(obj)
        }
    }

    override fun rotateObject(id: UUID) {
        val obj = when (val obj = objectMap[id]) {
            is WorkspaceObject.Position -> {
                obj.copy(
                    orientation = flipOrientation(obj.orientation)
                )
            }
            is WorkspaceObject.Transition -> {
                obj.copy(
                    orientation = flipOrientation(obj.orientation)
                )
            }
            else -> null
        }

        if (obj != null) {
            putToObjectMap(obj)
        }
    }

    override fun deleteObject(point: Point2D) {
        val id = findIdByPoint(point)
        if (id != null) {
            deleteObject(id)
        }
    }

    override fun rotateObject(point: Point2D) {
        val id = findIdByPoint(point)

        if (id != null) {
            rotateObject(id)
        }
    }

    override fun deleteObject(id: UUID) {
        deleteFromObjectMap(id)
    }


    override fun saveProject(file: File, projectId: UUID): SavedProjectInfo {
        return storageService.saveProject(
            file = file,
            data = ProjectData(
                id = projectId,
                items = objectMap.values.toList()
            )
        )
    }

    override fun findIdByPoint(point: Point2D): UUID? {
        return when (val obj = findContainsObject(point)) {
            is WorkspaceObject.Position -> obj.id
            is WorkspaceObject.Transition -> obj.id
            else -> null
        }
    }

    override fun getWorkspaceEditObjectState(clickPoint: Point2D, actualPoint: Point2D): WorkspaceEditObjectState? {
        return when (val obj = findContainsObject(clickPoint)) {
            is WorkspaceObject.Position -> WorkspaceEditObjectState.Position(
                id = obj.id,
                name = obj.name,
                description = obj.description,
                point = actualPoint,
                tokenNumber = obj.tokensNumber,
            )
            is WorkspaceObject.Transition -> WorkspaceEditObjectState.Transition(
                id = obj.id,
                name = obj.name,
                description = obj.description,
                point = actualPoint
            )
            else -> null
        }
    }

    override fun setObjects(objects: List<WorkspaceObject>) {
        objects.forEach(::putToObjectMap)
    }

    override fun collectMatrixBrief(): WorkspaceMatrixInputData {
        val objectValues = objectMap.values

        return WorkspaceMatrixInputData(
            timestamp = Instant.now(),
            items = objectValues.mapNotNull {
                it.toBrief()
            }
        )
    }

    override fun resetCurrentFocus() {
        val obj = objectMap.values.lastOrNull()

        when {
            obj is WorkspaceObject.Arc && obj.receiver is ArcReceiver.Point -> {
                deleteFromObjectMap(obj.id)
            }
            obj is WorkspaceObject.Position -> Unit
            obj is WorkspaceObject.Transition -> Unit
        }
    }

    override fun deleteObjects() {
        refreshLastUpdateTimestamp()
        objectMap.clear()
        refreshWorkspaceUndoObjectMap()
    }

    private fun findContainsObject(point: Point2D): WorkspaceObject? {
        return objectMap.values.find {
            it.containsPoint(point)
        }
    }

    private fun findLastArc(): WorkspaceObject.Arc? {
        return objectMap.values.findLast { it is WorkspaceObject.Arc } as? WorkspaceObject.Arc
    }

    private fun putToObjectMap(obj: WorkspaceObject) {
        if (BuildConfig.DEBUG) {
            val brief = obj.toBrief()
            if (brief != null) {
                safeLog(TAG) { brief }
            }
        }
        refreshLastUpdateTimestamp()
        objectMap[obj.id] = obj
        if (!(obj is CanMoving && obj.isMoving)) {
            refreshWorkspaceUndoObjectMap()
        }
    }

    private fun deleteFromObjectMap(id: UUID) {
        refreshLastUpdateTimestamp()
        objectMap.remove(id)
        deleteConnectedArcs(id)
        refreshWorkspaceUndoObjectMap()
    }

    override fun onListChanged(list: List<WorkspaceUndoObject>) {
        refreshLastUpdateTimestamp()
        objectMap.clear()
        list.forEach {
            objectMap[it.id] = it.toObject()
        }
        refreshWorkspaceUndoObjectMap()
    }

    private fun refreshLastUpdateTimestamp() {
        lastUpdateTimestamp = Instant.now()
    }

    private fun refreshWorkspaceUndoObjectMap() {
        workspaceUndoObjectMapProperty.value = objectMap.values.mapNotNull {
            it.toUndoObject()
        }
    }

    private fun deleteConnectedArcs(id: UUID) {
        val arcList = objectMap.filterValues {
            val source = ArcSource(id)
            val receiver = ArcReceiver.Uuid(id)
            (it is WorkspaceObject.Arc) && ((it.source == source) || (it.receiver == receiver))
        }.map { it.key }

        arcList.forEach(objectMap::remove)
    }

    private fun flipOrientation(orientation: Orientation): Orientation {
        return when (orientation) {
            Orientation.HORIZONTAL -> Orientation.VERTICAL
            Orientation.VERTICAL -> Orientation.HORIZONTAL
        }
    }
}