pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
    }
}

rootProject.name = "Elementa"


include(":mc-stubs")

include(":unstable:statev2")
include(":unstable:layoutdsl")


include(":example")
