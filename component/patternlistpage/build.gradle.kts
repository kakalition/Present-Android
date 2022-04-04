plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("common-plugin")
    id("dagger.hilt.android.plugin")
}

dependencies {

    implementation(project(Modules.domain))
    implementation(project(Modules.data))
    implementation(project(Modules.sharedassets))
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    addFragmentKtx()

    addSpek()
    addArchTesting()
}
android {
    buildFeatures {
        viewBinding = true
    }
}
