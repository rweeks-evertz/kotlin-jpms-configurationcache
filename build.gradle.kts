plugins {
    id("application")
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.3.20"
}

group = "io.github.rweeks.evertz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.3.20")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    mainClass = "io.github.rweeks.evertz.kotlin.Main"
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
    }
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks.named("compileJava", JavaCompile::class.java) {
    options.compilerArgumentProviders.add(CommandLineArgumentProvider {
        // Provide compiled Kotlin classes to javac – needed for Java/Kotlin mixed sources to work
        listOf("--patch-module", "test.module=${sourceSets["main"].output.asPath}")
    })
}
