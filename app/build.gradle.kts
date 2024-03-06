plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}




android {

    namespace = "com.example.drawingactivity"
    compileSdk = 34
    testBuildType = "debug"


    defaultConfig {
        applicationId = "com.example.drawingactivity"
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


    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildToolsVersion = "34.0.0"
}



dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("junit:junit:4.13.2")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation ("com.google.android.material:material:1.11.0")

// Espresso dependencies for UI testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test:rules:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-intents:3.0.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test:rules:1.5.0")

    debugImplementation("androidx.fragment:fragment-testing:1.6.2")
    debugImplementation("androidx.fragment:fragment-ktx:1.6.2")







// AndroidJUnitRunner and JUnit Rules

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest-all:1.3")

// Mockito for mocking in tests
    testImplementation ("org.mockito:mockito-core:4.4.0")
    androidTestImplementation ("org.mockito:mockito-android:4.4.0")

}

