package io.github.metrosampleapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import dev.zacsweers.metrox.viewmodel.metroViewModel
import io.github.metrosampleapp.ui.theme.MetroSampleAppTheme

class MainActivity : AppCompatActivity() {

    private val appGraph by lazy { (application as MainApplication).appGraph }

    private val metroViewModelFactory: MetroViewModelFactory by lazy {
        object : MetroViewModelFactory() {
            override val viewModelProviders = appGraph.viewModelProviders
            override val assistedFactoryProviders = appGraph.assistedFactoryProviders
            override val manualAssistedFactoryProviders = appGraph.manualAssistedFactoryProviders
        }
    }

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = metroViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalMetroViewModelFactory provides metroViewModelFactory) {

                val backStack = rememberNavBackStack(Routes.Home)
                MetroSampleAppTheme {
                    NavDisplay(
                        backStack = backStack,
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator(),
                        ),
                        entryProvider = entryProvider {
                            entry<Routes.Home> {
                                HomeScreen(
                                    vm = metroViewModel(),
                                    onNavigateToProfile = {
                                        backStack.add(Routes.Profile("Test"))
                                    }
                                )
                            }
                            entry<Routes.Profile> { route ->
                                val viewModel =
                                    assistedMetroViewModel<ProfileViewModel, ProfileViewModel.Factory> {
                                        create(route.name)
                                    }
                                ProfileScreen(
                                    vm = viewModel,
                                )
                            }
                        }
                    )
                }
            }

        }
    }
}

