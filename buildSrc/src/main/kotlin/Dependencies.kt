object ApplicationId {
    const val id = "com.daggery.nots"
}

object Modules {
    const val sharedassets = ":sharedassets"
    const val app = ":app"
    const val data = ":data"
    const val domain = ":domain"
}

object Release {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Version {
    const val kotlinVersion = "1.6.10"
    const val spek = "2.0.18"
    const val activityKtx = "1.4.0"
    const val androidTestCore = "1.4.0"
    const val fragmentKtx = "1.4.0"
    const val androidJunit = "1.1.3"
    const val androidJunitKtx = "1.1.3"
    const val appCompat = "1.4.1"
    const val archCoreCommon = "2.1.0"
    const val archCoreRuntime = "2.1.0"
    const val archCoreTesting = "2.1.0"
    const val compileSdkVersion = 31
    const val constraintLayout = "2.1.3"
    const val coreKtx = "1.7.0"
    const val coroutineCore = "1.6.0"
    const val espressoVersion = "3.4.0"
    const val fragmentVersion = "1.4.1"
    const val hiltAndroid = "2.39"
    const val hiltAndroidCompilerKapt = "2.39"
    const val junit = "4.13.2"
    const val junit5 = "5.8.2"
    const val kotlinJson = "1.3.2"
    const val leakCanary = "2.8.1"
    const val lifecycleRuntimeKtx = "2.4.1"
    const val lifecycleViewModelKtx = "2.4.1"
    const val materialDesign = "1.5.0"
    const val minSdkVersion = 26
    const val navigationFragmentKtx = "2.4.1"
    const val navigationUi = "2.4.1"
    const val preferencesDataStore = "1.0.0"
    const val recyclerView = "1.2.1"
    const val robolectric = "4.6"
    const val roomCompilerKapt = "2.4.1"
    const val roomKtx = "2.4.1"
    const val roomRuntime = "2.4.1"
    const val splashScreen = "1.0.0-beta01"
    const val targetSdkVersion = 31
    const val testRulesVersion = "1.4.0"
    const val testRunnerVersion = "1.4.0"
}

object Libraries {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hiltAndroid}"
    const val hiltAndroidCompilerKapt = "com.google.dagger:hilt-android-compiler:${Version.hiltAndroidCompilerKapt}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Version.leakCanary}"
}

object KotlinLibraries {
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutineCore}"
    const val kotlinJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.kotlinJson}"
}

object AndroidLibraries {
    const val activityKtx = "androidx.activity:activity-ktx:${Version.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
    const val preferencesDataStore = "androidx.datastore:datastore-preferences:${Version.preferencesDataStore}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleRuntimeKtx}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleViewModelKtx}"
    const val materialDesign = "com.google.android.material:material:${Version.materialDesign}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Version.navigationFragmentKtx}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Version.navigationUi}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerView}"
    const val roomCompilerKapt = "androidx.room:room-compiler:${Version.roomCompilerKapt}"
    const val roomKtx = "androidx.room:room-ktx:${Version.roomKtx}"
    const val roomRuntime = "androidx.room:room-runtime:${Version.roomRuntime}"
    const val splashScreen = "androidx.core:core-splashscreen:${Version.splashScreen}"
}

object TestLibraries {
    const val archCoreCommon = "androidx.arch.core:core-common:${Version.archCoreCommon}"
    const val archCoreRuntime = "androidx.arch.core:core-runtime:${Version.archCoreRuntime}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Version.archCoreTesting}"
    const val junit = "junit:junit:${Version.junit}"
    const val junit5 = "org.junit.jupiter:junit-jupiter:${Version.junit5}"
    const val robolectric = "org.robolectric:robolectric:${Version.robolectric}"
    const val spekDsl = "org.spekframework.spek2:spek-dsl-jvm:${Version.spek}"
    const val spekRunner = "org.spekframework.spek2:spek-runner-junit5:${Version.spek}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlinVersion}"
}

object AndroidTestLibraries {
    const val androidTestCore = "androidx.test:core:${Version.androidTestCore}"
    const val androidTestCoreKtx ="androidx.test:core-ktx:${Version.androidTestCore}"
    const val androidJunit = "androidx.test.ext:junit:${Version.androidJunit}"
    const val androidJunitKtx = "androidx.test.ext:junit-ktx:${Version.androidJunitKtx}"
    const val androidJunitRunner = "androidx.test:runner:${Version.testRunnerVersion}"
    const val androidJunitRules = "androidx.test:rules:${Version.testRulesVersion}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espressoVersion}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Version.espressoVersion}"
    const val espressoIntent = "androidx.test.espresso:espresso-intents:${Version.espressoVersion}"
    const val espressoAccessibility = "androidx.test.espresso:espresso-accessibility:${Version.espressoVersion}"
    const val espressoIdlingConcurrent = "androidx.test.espresso.idling:idling-concurrent:${Version.espressoVersion}"
    const val espressoIdlingResource = "androidx.test.espresso:espresso-idling-resource:${Version.espressoVersion}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Version.fragmentVersion}"
}
