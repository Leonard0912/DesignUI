plugins {
    `java-library`
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":"))
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))

application {
    mainClass.set("gg.essential.elementa.example.Main")
}
