plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("common-plugin")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(Modules.domain))
    addRoom()
}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    correctErrorTypes = true
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}