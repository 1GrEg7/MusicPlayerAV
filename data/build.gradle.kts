plugins {
    alias(libs.plugins.android.library)
    //alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    id ("org.jetbrains.kotlin.android")
}

configurations.all {
    resolutionStrategy {
        // Принудительно используем Kotlin 1.9.22
        force( "org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
        force ("org.jetbrains.kotlin:kotlin-reflect:1.9.22")
    }
}

android {
    namespace = "data"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.9.22")

//    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":core"))
    implementation(project(":domain"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.kotlinx.serialization.json)

    //Retrofit
    implementation (libs.retrofit)
    //implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation (libs.converter.gson)

    // OkHttp (HTTP-клиент)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    implementation ("androidx.room:room-runtime:2.7.1")
    kapt ("androidx.room:room-compiler:2.7.1")
    implementation("androidx.room:room-ktx:2.7.1")
}