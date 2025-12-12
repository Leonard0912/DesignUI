plugins {
    `java-library`
    application
    id("gg.essential.defaults.repo")
}

dependencies {
    implementation(libs.universalcraft.standalone)
    implementation(project(":"))
    implementation(project(":unstable:layoutdsl"))
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))

application {
    mainClass.set("gg.essential.elementa.example.Main")
}
