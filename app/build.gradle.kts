plugins {
    id("com.android.application")
}

android {
    namespace = "io.github.marcolucidi01.quickmediamute"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.marcolucidi01.quickmediamute"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        base.archivesName = "$applicationId-v$versionName"
    }

    buildTypes {
        debug {
            isDebuggable = true
            versionNameSuffix = "-debug"
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            // without this line, my phone thinks that the .apk is invalid, i.e. it won't install unsigned .apks
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}