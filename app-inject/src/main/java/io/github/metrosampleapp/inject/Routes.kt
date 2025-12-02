package io.github.metrosampleapp.inject

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Routes : NavKey {

    @Serializable
    data object Home : Routes

    @Serializable
    data class Profile(val name: String) : Routes
}