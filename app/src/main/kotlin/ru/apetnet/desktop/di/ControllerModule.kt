package ru.apetnet.desktop.di

import com.google.inject.Singleton
import dev.misfitlabs.kotlinguice4.KotlinModule
import ru.apetnet.desktop.ui.controller.main.MainController
import ru.apetnet.desktop.ui.controller.main.MainControllerImpl
import ru.apetnet.desktop.ui.controller.matrix.exportmatrix.ExportMatrixController
import ru.apetnet.desktop.ui.controller.matrix.exportmatrix.ExportMatrixControllerImpl
import ru.apetnet.desktop.ui.controller.matrix.importmatrix.ImportMatrixController
import ru.apetnet.desktop.ui.controller.matrix.importmatrix.ImportMatrixControllerImpl
import ru.apetnet.desktop.ui.controller.matrix.matrixanalysis.MatrixAnalysisController
import ru.apetnet.desktop.ui.controller.matrix.matrixanalysis.MatrixAnalysisControllerImpl
import ru.apetnet.desktop.ui.controller.reachabilitytree.ReachabilityTreeController
import ru.apetnet.desktop.ui.controller.reachabilitytree.ReachabilityTreeControllerImpl
import ru.apetnet.desktop.ui.controller.workspace.WorkspaceController
import ru.apetnet.desktop.ui.controller.workspace.WorkspaceControllerImpl

class ControllerModule : KotlinModule() {
    override fun configure() {
        bind<MainController>().to<MainControllerImpl>().`in`<Singleton>()
        bind<WorkspaceController>().to<WorkspaceControllerImpl>()
        bind<ImportMatrixController>().to<ImportMatrixControllerImpl>()
        bind<ExportMatrixController>().to<ExportMatrixControllerImpl>()
        bind<ReachabilityTreeController>().to<ReachabilityTreeControllerImpl>()
        bind<MatrixAnalysisController>().to<MatrixAnalysisControllerImpl>()
    }
}