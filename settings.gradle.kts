pluginManagement {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    plugins {
        id("com.android.application") version "7.1.2" apply false
        id("com.android.library") version "7.1.2" apply false
        id("org.jetbrains.kotlin.android") version "1.6.10" apply false
        id("org.jetbrains.kotlin.jvm") version "1.6.10" apply false
    }

}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Present"

include(":app")
include(":domain")
include(":data")
include(":component:breathpage")
include(":component:dashboardpage")
include(":component:patternlistpage")
include(":component:reminderpage")
include(":sharedassets")
