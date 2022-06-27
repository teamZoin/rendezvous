repositories {
    google()
}
dependencies {
    implementation(project(":rendezvous-core"))

    implementation("javax.inject:javax.inject:_")

    // mail
    implementation("com.sun.mail:jakarta.mail:_")
    implementation("com.sun.activation:jakarta.activation:_")
    implementation("com.google.api-client:google-api-client:_")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:_")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:_")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:_")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:_")
    implementation("com.squareup.retrofit2:converter-jackson:_")
}
