import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.2.0"
    id("fabric-loom") version "1.11-SNAPSHOT"
    `maven-publish`
    java
}

group = "eighty7"
version = "25.3"

val kotlin = "2.2.0"
val minecraft = "1.21.8"

repositories {

    mavenCentral()
}

dependencies {

    minecraft("com.mojang:minecraft:$minecraft")
    mappings("net.fabricmc:yarn:$minecraft+build.1:v2")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.129.0+1.21.8")
    modImplementation("net.fabricmc:fabric-loader:0.16.14")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.13.4+kotlin.$kotlin")

    implementation(kotlin("stdlib:$kotlin"))
    implementation(kotlin("stdlib-jdk8:$kotlin"))

    implementation("org.luaj:luaj-jme:3.0.1")
    implementation("org.luaj:luaj-jse:3.0.1")
}

tasks {

    processResources {

        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {

            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }

    publishing {

        publications {

            create<MavenPublication>("mavenJava") {

                artifact(remapJar) {

                    builtBy(remapJar)
                }
                artifact(kotlinSourcesJar) {

                    builtBy(remapSourcesJar)
                }
            }
        }

        repositories {

        }
    }

    compileKotlin {

        compilerOptions {

            jvmTarget = JvmTarget.JVM_21
        }
    }
}