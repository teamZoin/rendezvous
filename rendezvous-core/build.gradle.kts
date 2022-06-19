plugins {
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.21"
    kotlin("kapt")
}

dependencies {
    implementation("javax.inject:javax.inject:_")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")
    implementation("mysql:mysql-connector-java:_")

    implementation("com.querydsl:querydsl-jpa:_")
    kapt("com.querydsl:querydsl-apt:_:jpa")
}

val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false
jar.enabled = true

sourceSets {
    main {
        java {
            setSrcDirs(
                listOf(
                    projectDir.path + "/src/main/kotlin",
                    projectDir.path + "/build/generated"
                )
            )
        }
    }
}
