package ru.apetnet.desktop.service.matrix.matrixanalysis

import ru.apetnet.desktop.domain.service.matrixanalysis.MurataAnalysisSolution
import ru.apetnet.desktop.domain.ui.matrix.WorkspaceObjectBrief

interface MurataAnalysisService {
    fun collectSolution(
        items: List<WorkspaceObjectBrief>,
        withVerifying: Boolean = true
    ): MurataAnalysisSolution
}