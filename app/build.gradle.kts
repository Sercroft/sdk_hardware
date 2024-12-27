plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    val DEFAULT_DIMENSION = "default_dimension"

    namespace = "com.credibanco.sdkhardware"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.credibanco.sdkhardware"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        create("Nexgo"){
            dimension = DEFAULT_DIMENSION
            buildConfigField("String", "APP_NAME", "\"Credibanco (Nexgo)\"")
        }
        create("Ingenico"){
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

    implementation(project(":SDK"))

    implementation(libs.androidx.core.ktx)
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(project(mapOf("path" to ":SDK")))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

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
