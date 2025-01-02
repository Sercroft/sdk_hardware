plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    val DEFAULT_DIMENSION = "default_dimension"

    namespace = "com.credibanco.sdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }

    flavorDimensions (DEFAULT_DIMENSION)
    productFlavors {
        create("Nexgo") {
            dimension = DEFAULT_DIMENSION
            buildConfigField("String", "APP_NAME", "\"Credibanco (Nexgo)\"")
        }
        create("Ingenico") {
            dimension = DEFAULT_DIMENSION
            buildConfigField("String", "APP_NAME", "\"Credibanco (Ingenico)\"")
        }
    }
}

// VERSIONS
val daggerHiltVersion   = rootProject.extra.get("dagger_hilt_version") as String
val retrofitVersion     = rootProject.extra.get("retrofit_version") as String
val coroutines          = rootProject.extra.get("coroutines_version") as String

dependencies {

    "NexgoCompileOnly"(
        files("C:\\Users\\USUARIO\\Documents\\SDK HARDWARE\\SDK\\libs\\nexgo_smartpos_sdk_v3_04_001__20211014.aar")
    )

    "IngenicoImplementation"(
        files("C:\\Users\\USUARIO\\Documents\\SDK HARDWARE\\SDK\\libs\\ingenico_usdk_api_aidl_limited_v20120210628.jar")
    )
    "IngenicoImplementation"(
        files("C:\\Users\\USUARIO\\Documents\\SDK HARDWARE\\SDK\\libs\\ingenico_usdk_api_aidl_v13_11_0_20231102.jar")
    )

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation ("com.google.code.gson:gson:2.8.5")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
