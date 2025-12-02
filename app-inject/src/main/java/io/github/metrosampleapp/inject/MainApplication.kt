package io.github.metrosampleapp.inject

import android.app.Application
import dev.zacsweers.metro.createGraphFactory
import io.github.metrosampleapp.inject.di.AppGraph

class MainApplication : Application() {

    val appGraph: AppGraph by lazy {
        createGraphFactory<AppGraph.Factory>().create(this)
    }
}