package ru.apetnet.desktop.di

import dev.misfitlabs.kotlinguice4.KotlinModule
import ru.apetnet.desktop.service.matrix.importmatrix.ImportMatrixService
import ru.apetnet.desktop.service.matrix.importmatrix.ImportMatrixServiceImpl
import ru.apetnet.desktop.service.matrix.MatrixViewService
import ru.apetnet.desktop.service.matrix.MatrixViewServiceImpl
import ru.apetnet.desktop.service.matrix.exportmatrix.ExportMatrixService
import ru.apetnet.desktop.service.matrix.exportmatrix.ExportMatrixServiceImpl
import ru.apetnet.desktop.service.matrix.helpers.MatrixHelperService
import ru.apetnet.desktop.service.matrix.helpers.MatrixHelperServiceImpl
import ru.apetnet.desktop.service.matrix.matrixanalysis.MurataAnalysisService
import ru.apetnet.desktop.service.matrix.matrixanalysis.MurataAnalysisServiceImpl
import ru.apetnet.desktop.service.matrix.reachabilitytree.ReachabilityTreeService
import ru.apetnet.desktop.service.matrix.reachabilitytree.ReachabilityTreeServiceImpl
import ru.apetnet.desktop.service.storage.StorageService
import ru.apetnet.desktop.service.storage.StorageServiceImpl
import ru.apetnet.desktop.service.workspace.PnWorkspaceService
import ru.apetnet.desktop.service.workspace.PnWorkspaceServiceImpl

class ServiceModule : KotlinModule() {
    override fun configure() {
        bind<PnWorkspaceService>().to<PnWorkspaceServiceImpl>()
        bind<StorageService>().to<StorageServiceImpl>()
        bind<ImportMatrixService>().to<ImportMatrixServiceImpl>()
        bind<MatrixViewService>().to<MatrixViewServiceImpl>()
        bind<ExportMatrixService>().to<ExportMatrixServiceImpl>()
        bind<ReachabilityTreeService>().to<ReachabilityTreeServiceImpl>()
        bind<MatrixHelperService>().to<MatrixHelperServiceImpl>()
        bind<MurataAnalysisService>().to<MurataAnalysisServiceImpl>()
    }
}