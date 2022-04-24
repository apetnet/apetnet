package ru.apetnet.desktop.di

import dev.misfitlabs.kotlinguice4.KotlinModule
import ru.apetnet.matutil.linearsolver.api.HomogeneousLinearSolver
import ru.apetnet.matutil.linearsolver.impl.HomogeneousLinearSolverImpl2
import ru.apetnet.pnutil.reachabilitytreefinder.ReachabilityTreeFinder
import ru.apetnet.pnutil.reachabilitytreefinder.impl.ReachabilityTreeFinderImpl

class ExternalModule : KotlinModule() {
    override fun configure() {
        bind<ReachabilityTreeFinder>().to<ReachabilityTreeFinderImpl>()
        bind<HomogeneousLinearSolver>().to<HomogeneousLinearSolverImpl2>()
    }
}