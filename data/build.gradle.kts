plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("common-plugin")
}

dependencies {

    addAndroidCore()
    addJunit5()
    addSpek()
    addEspresso()
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}