import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.metro) apply false
    alias(libs.plugins.serialization) apply false
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-opt-in=kotlin.time.ExperimentalTime",
                "-opt-in=kotlin.concurrent.atomics.ExperimentalAtomicApi",
                "-opt-in=kotlin.uuid.ExperimentalUuidApi",
            )
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}