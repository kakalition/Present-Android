plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("common-plugin")
}

dependencies {

    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.sharedassets))
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    addFragmentKtx()
    addNavigation()

    addSpek()
    addArchTesting()
}