plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("common-plugin")
}

dependencies {

    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.sharedassets))
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")

    addFragmentKtx()

    addSpek()
    addArchTesting()
}