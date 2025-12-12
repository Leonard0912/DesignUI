plugins {
    `java-library`
    `maven-publish`
}

group = "gg.essential"
version = "1.0.0-SNAPSHOT"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.0")
    compileOnly("org.commonmark:commonmark:0.21.0")
    compileOnly("org.dom4j:dom4j:2.1.4")
    compileOnly(project(":mc-stubs"))
    compileOnly("org.lwjgl:lwjgl-opengl:3.3.1")
    compileOnly("com.google.code.gson:gson:2.2.4")
}

tasks.processResources {
    inputs.property("project.version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

java.withSourcesJar()

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "elementa"
        }
    }
}
