plugins {
    `java-library`
    `maven-publish`
}

version = "1.0.0-SNAPSHOT"
group = "gg.essential"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation(project(":"))
}

tasks.test {
    useJUnitPlatform()
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))

java.withSourcesJar()

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "elementa-unstable-${project.name}"
        }
    }
}