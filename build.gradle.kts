group="endredeak"

plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("com.google.code.gson:gson:2.10")
}