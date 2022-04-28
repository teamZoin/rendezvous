dependencies {
    implementation(project(":rendezvous-core"))
    implementation(project(":rendezvous-data"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}