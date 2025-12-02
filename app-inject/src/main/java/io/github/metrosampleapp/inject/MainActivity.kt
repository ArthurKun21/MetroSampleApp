package io.github.metrosampleapp.inject

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import dev.zacsweers.metrox.viewmodel.metroViewModel
import io.github.metrosampleapp.inject.ui.theme.MetroSampleAppTheme

// `@ContributesIntoMap`-annotated class @io.github.metrosampleapp.inject.MainActivity must declare a map key but doesn't. Add one on the explicit bound type or the class.

@ContributesIntoMap(AppScope::class, binding<Activity>())
//@ActivityKey(MainActivity::class) // ActivityKey on metrox-android
@Inject
class MainActivity(private val metroViewModelFactory: MetroViewModelFactory) : AppCompatActivity() {

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

