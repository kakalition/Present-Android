import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class CommonPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("org.jetbrains.kotlin.android")
        project.pluginManager.apply("kotlin-kapt")

        val androidExtension = project.extensions.getByName("android")
        if(androidExtension is BaseExtension)
            androidExtension.apply {
                compileSdkVersion(Version.compileSdkVersion)

                defaultConfig {
                    minSdk = Version.minSdkVersion
                    targetSdk = Version.targetSdkVersion

                    versionCode = Release.versionCode
                    versionName = Release.versionName

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                val proguardFile = "proguard-rules.pro"
                when(this) {
                    is LibraryExtension -> defaultConfig {
                        consumerProguardFile(proguardFile)
                    }
                    is AppExtension -> buildTypes {
                        getByName("release") {
                            isMinifyEnabled = true
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                "proguard-rules.pro"
                            )
                        }
                    }
                }

                buildFeatures.viewBinding = true

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }

                project.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = "1.8"
                    }
                }
            }
    }
}