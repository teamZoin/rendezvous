dependencies {
    implementation(project(":rendezvous-core"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:_")
    implementation("mysql:mysql-connector-java:_")
}

val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false
jar.enabled = true
