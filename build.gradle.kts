plugins {
    kotlin("jvm") version "1.8.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.json:json:20211205")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}