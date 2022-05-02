dependencies {
    implementation(project(":rendezvous-core"))
    implementation(project(":rendezvous-data"))
    implementation(project(":rendezvous-infra"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:_")

    // for infra
    implementation("com.sun.mail:jakarta.mail:_")
    implementation("com.sun.activation:jakarta.activation:_")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}