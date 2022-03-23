plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("common-plugin")
}

dependencies {

    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.sharedassets))

    addFragmentKtx()

    addSpek()
    addArchTesting()
}