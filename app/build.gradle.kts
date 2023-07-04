typealias and = com.example.internal.Android
typealias dep = com.example.internal.Dependencies

plugins {
    id("dagger.hilt.android.plugin")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
}

android {
    namespace = "com.example.fafcalculator"
    compileSdk = and.compileSdk

    defaultConfig {
        applicationId = "com.example.fafcalculator"
        minSdk = and.minSdk
        targetSdk = and.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    //implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")

    dep.hilt.apply { // https://dagger.dev/hilt/
        implementation(hiltAndroid)
        kapt(daggerHiltCompiler)
        kaptAndroidTest(daggerHiltCompiler)
    }

    dep.androidX.apply {
        implementation(ktxCore)
        implementation(ktxActivity)
        implementation(ktxFragment)
        implementation(appcompat)
        implementation(constraintLayout)
        implementation(navigationFragment)
        implementation(navigationUi)
    }

    dep.other.apply {// Miscellaneous required libraries
        implementation(material)
        implementation(coroutines)
        implementation(viewBindingPropDel)
        implementation(viewBindingPropDelNoRef)
    }

    dep.room.apply { // https://developer.android.com/jetpack/androidx/releases/room
        implementation(runtime)
        implementation(ktx)
        kapt(compiler)
    }

    dep.test.apply {
        testImplementation(junit)
    }
}