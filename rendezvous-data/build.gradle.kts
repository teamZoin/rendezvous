dependencies {
    implementation(project(":rendezvous-core"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.7")
    implementation("mysql:mysql-connector-java:8.0.29")
}

val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false
jar.enabled = true