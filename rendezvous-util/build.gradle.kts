dependencies {
    implementation(project(":rendezvous-core"))
    implementation("javax.inject:javax.inject:_")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:_")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:_")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:_")
}
