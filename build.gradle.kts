plugins {
    kotlin("jvm") version "2.2.20"
}

group = "me.shurikennen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

kotlin {
    jvmToolchain(24)
}