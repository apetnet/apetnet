package ru.apetnet.desktop.service.matrix

import javafx.geometry.Orientation
import javafx.geometry.Point2D
import org.ejml.simple.SimpleMatrix
import ru.apetnet.desktop.BuildConfig
import ru.apetnet.desktop.app.Application
import ru.apetnet.desktop.domain.service.matrixview.CollectObjectsResult
import ru.apetnet.desktop.domain.service.matrixview.ImportedProjectInfo
import ru.apetnet.desktop.domain.service.matrixview.MatrixedObjectType
import ru.apetnet.desktop.domain.ui.workspace.items.*
import ru.apetnet.ext.ejml.forEachIndexed
import ru.apetnet.ext.ejml.numCols
import ru.apetnet.ext.ejml.numRows
import ru.apetnet.ext.fx.java.geometry.point2D
import tornadofx.Point2D

class MatrixViewServiceImpl : MatrixViewService {
    companion object {
        private const val INTENT = 30.0

        private const val POSITION_FACTOR = BuildConfig.POSITION_DIAMETER + INTENT

        private const val TRANSITION_FACTOR = BuildConfig.TRANSITION_LARGER_SIZE + INTENT

        private val MAX_WORKSPACE_POSITIONS =
            (Application.WINDOW_WIDTH / (BuildConfig.POSITION_DIAMETER + INTENT)).toInt()

        private val MAX_WORKSPACE_TRANSITIONS: Int =
            (Application.WINDOW_WIDTH / (BuildConfig.TRANSITION_LARGER_SIZE + INTENT)).toInt()
    }

    override fun importProject(
        matI: SimpleMatrix,
        matO: SimpleMatrix,
        matMarking: SimpleMatrix,
        positionNames: List<String>,
        transitionNames: List<String>,
        orientation: Orientation
    ): ImportedProjectInfo {
        val positionsResult = collectPositions(
            matI = matI,
            matMarking = matMarking,
            names = positionNames,
            nextPoint = Point2D(1.0),
            orientation = orientation
        )

        val transitionList = collectTransitions(
            matI = matI,
            names = transitionNames,
            nextPoint = positionsResult.nextPoint,
            orientation = orientation
        ).list

        val positionList = positionsResult.list

        val arcList = collectArcs(
            matI = matI,
            matO = matO,
            positionList = positionList,
            transitionList = transitionList
        )

        return ImportedProjectInfo(
            items = positionList + transitionList + arcList
        )
    }

    private fun collectPositions(
        matI: SimpleMatrix,
        matMarking: SimpleMatrix,
        names: List<String>,
        nextPoint: Point2D,
        orientation: Orientation
    ): CollectObjectsResult<WorkspaceObject.Position> {
        val list = mutableListOf<WorkspaceObject.Position>()
        var p = nextPoint

        repeat(matI.numRows) { pId ->
            list.add(
                workspacePositionOf(
                    index = pId.toLong(),
                    name = names[pId],
                    point = calculatePoint(
                        type = MatrixedObjectType.POSITION,
                        np = p
                    ),
                    tokensNumber = matMarking.get(0, pId)
                        .toInt(),
                    orientation = orientation
                )
            )

            p = nextPoint(
                objectType = MatrixedObjectType.POSITION,
                point = p
            )
        }

        p = point2D(
            x = 1.0,
            y = (p.y + 1)
        )

        return CollectObjectsResult(
            nextPoint = p,
            list = list
        )
    }

    private fun collectTransitions(
        matI: SimpleMatrix,
        names: List<String>,
        nextPoint: Point2D,
        orientation: Orientation
    ): CollectObjectsResult<WorkspaceObject.Transition> {
        val list = mutableListOf<WorkspaceObject.Transition>()
        var p = nextPoint

        repeat(matI.numCols) { tId ->
            list.add(
                workspaceTransitionOf(
                    index = tId.toLong(),
                    name = names[tId],
                    point = calculatePoint(
                        type = MatrixedObjectType.TRANSITION,
                        np = p
                    ),
                    orientation = orientation
                )
            )

            p = nextPoint(
                objectType = MatrixedObjectType.TRANSITION,
                point = p
            )
        }

        p = point2D(
            x = 1.0,
            y = (p.y + 1)
        )

        return CollectObjectsResult(
            nextPoint = p,
            list = list
        )
    }

    private fun collectArcs(
        positionList: List<WorkspaceObject.Position>,
        transitionList: List<WorkspaceObject.Transition>,
        matI: SimpleMatrix,
        matO: SimpleMatrix,
    ): List<WorkspaceObject.Arc> {
        val list = mutableListOf<WorkspaceObject.Arc>()

        matI.forEachIndexed { pId, tId, value ->
            if (value > 0) {
                list.add(
                    createArc(
                        index = list.size,
                        type = ArcType.FROM_POSITION,
                        pId = pId,
                        tId = tId,
                        positionList = positionList,
                        transitionList = transitionList
                    )
                )
            }
        }

        matO.forEachIndexed { pId, tId, value ->
            if (value > 0) {
                list.add(
                    createArc(
                        index = list.size,
                        type = ArcType.FROM_TRANSITION,
                        pId = pId,
                        tId = tId,
                        positionList = positionList,
                        transitionList = transitionList
                    )
                )
            }
        }

        return list
    }

    private fun nextPoint(
        objectType: MatrixedObjectType,
        point: Point2D
    ): Point2D {
        val max = when (objectType) {
            MatrixedObjectType.POSITION -> MAX_WORKSPACE_POSITIONS
            MatrixedObjectType.TRANSITION -> MAX_WORKSPACE_TRANSITIONS
        }


        var x = point.x
        var y = point.y

        x++

        if (x > max) {
            y += 1
            x = 1.0
        }

        return point2D(x, y)
    }

    private fun createArc(
        index: Int,
        type: ArcType,
        pId: Int,
        tId: Int,
        positionList: List<WorkspaceObject.Position>,
        transitionList: List<WorkspaceObject.Transition>
    ): WorkspaceObject.Arc {
        val position = positionList[pId]
        val transition = transitionList[tId]

        val (sourceId, receiverId) = when (type) {
            ArcType.FROM_POSITION -> position.id to transition.id
            ArcType.FROM_TRANSITION -> transition.id to position.id
        }

        return workspaceArcOf(
            index = index.toLong(),
            source = ArcSource(sourceId),
            receiver = ArcReceiver.Uuid(receiverId),
            type = type
        )
    }

    private fun calculatePoint(
        type: MatrixedObjectType,
        np: Point2D
    ): Point2D {
        /*
        val rand = Random()
        val x = rand.nextDouble() * 500 (width)
        val y = rand.nextDouble() * 500 (height)
         */

        val factor = when (type) {
            MatrixedObjectType.POSITION -> POSITION_FACTOR
            MatrixedObjectType.TRANSITION -> TRANSITION_FACTOR
        }

        return point2D(
            x = (np.x * factor),
            y = (np.y * factor)
        )
    }
}