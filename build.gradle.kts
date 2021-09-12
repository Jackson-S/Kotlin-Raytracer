import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.2.0"
}

group = "me.jackson"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "13"
}
