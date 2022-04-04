plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("common-plugin")
}

dependencies {

    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.sharedassets))
    implementation(project(Modules.breathpage))
    implementation(project(Modules.patternlistpage))
/*
    implementation(project(Modules.dashboardpage))
    implementation(project(Modules.notificationpage))
    implementation(project(Modules.routinepage))
*/
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    addFragmentKtx()
    addNavigation()
    addRoom()

    addSpek()
    addArchTesting()
}