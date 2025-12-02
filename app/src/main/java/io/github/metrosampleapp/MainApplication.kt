package io.github.metrosampleapp

import android.app.Application
import dev.zacsweers.metro.createGraphFactory

class MainApplication : Application() {

    val appGraph: AppGraph by lazy {
        createGraphFactory<AppGraph.Factory>().create(this)
    }
}