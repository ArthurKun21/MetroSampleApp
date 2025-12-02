package io.github.metrosampleapp

import android.app.Application
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metrox.viewmodel.MetroViewModelMultibindings

@DependencyGraph(AppScope::class)
@SingleIn(AppScope::class)
interface AppGraph : MetroViewModelMultibindings {
    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides application: Application): AppGraph
    }
}