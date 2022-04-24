package ru.apetnet.desktop.service.workspace

import javafx.beans.property.ReadOnlyObjectProperty
import javafx.geometry.Orientation
import javafx.geometry.Point2D
import ru.apetnet.desktop.domain.service.storage.SavedProjectInfo
import ru.apetnet.desktop.domain.service.workspace.WorkspaceObjectModel
import ru.apetnet.desktop.domain.ui.workspace.editobject.WorkspaceEditObjectState
import ru.apetnet.desktop.domain.ui.workspace.items.WorkspaceObject
import ru.apetnet.desktop.domain.ui.workspace.matrix.WorkspaceMatrixInputData
import ru.apetnet.desktop.util.jundo.objects.WorkspaceUndoObject
import java.io.File
import java.math.BigInteger
import java.util.*

interface PnWorkspaceService {
    val workspaceUndoObjectMapProperty: ReadOnlyObjectProperty<List<WorkspaceUndoObject>>
    val workspaceObjectModel: WorkspaceObjectModel

    fun addPosition(
        point: Point2D,
        orientation: Orientation
    )

    fun addTransition(
        point: Point2D,
        orientation: Orientation
    )

    fun findIdByPoint(
        point: Point2D
    ): UUID?

    fun getWorkspaceEditObjectState(
        clickPoint: Point2D,
        actualPoint: Point2D = clickPoint
    ): WorkspaceEditObjectState?

    fun addArc(
        point: Point2D
    )

    fun setArcDirectionPoint(
        point: Point2D
    )

    fun moveObject(
        id: UUID,
        point: Point2D
    )

    fun stopObjectMoving(
        id: UUID
    )

    fun deleteObject(
        point: Point2D
    )

    fun rotateObject(
        point: Point2D
    )

    fun setTokenNumber(
        id: UUID,
        number: Int
    )

    fun setObjectName(
        id: UUID,
        name: String
    )

    fun rotateObject(
        id: UUID
    )

    fun deleteObject(
        id: UUID
    )

    fun setObjectDescription(
        id: UUID,
        description: String
    )

    fun saveProject(
        file: File, projectId: UUID
    ): SavedProjectInfo

    fun setObjects(
        objects: List<WorkspaceObject>
    )

    fun collectMatrixBrief(): WorkspaceMatrixInputData

    fun resetCurrentFocus()

    fun deleteObjects()
}