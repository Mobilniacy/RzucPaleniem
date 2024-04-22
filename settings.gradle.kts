pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/data2viz/p/maven/dev")
        maven("https://maven.pkg.jetbrains.space/data2viz/p/maven/public")
        maven("https://jitpack.io")
    }
}

rootProject.name = "RzucPaleniem"
include(":app")
