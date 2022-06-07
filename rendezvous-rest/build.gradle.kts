import com.google.cloud.tools.jib.gradle.JibExtension

// plugins {
//     id("org.asciidoctor.jvm.convert")
// }
//

configure<JibExtension> {
    jib {
        from {
            image = "eclipse-temurin:17.0.3_7-jre"
            platforms {
                platform {
                    architecture = "amd64"
                    os = "linux"
                }
            }
        }
        to {
            image = "564549038106.dkr.ecr.ap-northeast-2.amazonaws.com/${project.name}"
        }
        container {
            creationTime = "USE_CURRENT_TIMESTAMP"
            environment = mapOf(
                "USE_PROFILE" to "dev"
            )
            jvmFlags = listOf(
                "-Dspring.profiles.active=\$USE_PROFILE",
            )
        }
    }
}

dependencies {
    implementation(project(":rendezvous-core"))
    implementation(project(":rendezvous-infra"))
    implementation(project(":rendezvous-util"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:_")
    implementation("org.springframework.cloud:spring-cloud-starter-aws-secrets-manager-config:_")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:_")

    // for infra
    implementation("com.sun.mail:jakarta.mail:_")
    implementation("com.sun.activation:jakarta.activation:_")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:_")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:_")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:_")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc:_")
}

// val asciidocSnippetsDir by extra { file("build/generated-snippets") }
//
// tasks.test {
//     outputs.dir(asciidocSnippetsDir)
// }
//
// tasks.asciidoctor {
//     inputs.dir(file(asciidocSnippetsDir))
//     attributes(mapOf("snippets" to asciidocSnippetsDir))
//     dependsOn(tasks.test)
// }
//
// tasks.bootJar {
//     dependsOn(tasks.asciidoctor)
//     from("${project.buildDir}/docs/asciidoc") {
//         into("static/docs")
//     }
// }
