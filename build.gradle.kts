import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    idea
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("com.google.cloud.tools.jib") version "3.2.1" apply false
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("kapt") version "1.6.21"
}

group = "com.zoin"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val springProjects = listOf(
    project(":rendezvous-core"),
    project(":rendezvous-rest"),
)

configure(springProjects) {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.google.cloud.tools.jib")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation("io.kotest.extensions:kotest-extensions-spring:_")
        testImplementation("com.ninja-squad:springmockk:_")
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-allopen")

    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("io.github.microutils:kotlin-logging:_")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:_")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")

        // okhttp
        implementation("com.squareup.okhttp3:okhttp:_")
        implementation("com.squareup.okhttp3:logging-interceptor:_")

        testImplementation("io.mockk:mockk:_")
        testImplementation("io.kotest:kotest-runner-junit5:_")
        testImplementation("io.kotest:kotest-assertions-core:_")
        testImplementation("io.kotest:kotest-property:_")
        testImplementation("io.kotest:kotest-framework-datatest:_")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
    }

    allOpen {
        annotations(
            "javax.inject.Named",
            "javax.transaction.Transactional",
            "jakarta.inject.Named",
        )
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
