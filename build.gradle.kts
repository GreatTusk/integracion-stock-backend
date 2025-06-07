plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.integracion"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

ktor {
    docker {
        customBaseImage = "openjdk:17-slim"
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName.set("integracion-backend")
        imageTag.set("latest")
    }
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.rate.limit)
    implementation(libs.ktor.server.auth)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.postgresql)
    implementation(libs.h2)
    implementation(libs.exposed.core)
    implementation(libs.exposed.migration)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
