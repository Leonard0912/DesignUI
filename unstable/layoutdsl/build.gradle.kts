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
    api(project(":unstable:statev2"))
    compileOnly("org.lwjgl:lwjgl-opengl:3.3.1")
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