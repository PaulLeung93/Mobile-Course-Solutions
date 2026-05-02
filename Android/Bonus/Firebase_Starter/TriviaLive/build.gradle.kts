// Top-level build file. Configuration here applies to all subprojects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Part 1 — Add the Google Services plugin here after creating your Firebase project:
    // id("com.google.gms.google-services") version "4.4.4" apply false
}
