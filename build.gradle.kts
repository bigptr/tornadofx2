plugins {
    kotlin("jvm") version "1.7.20"
    `java-library`
    id("org.openjfx.javafxplugin") version "0.0.14"
}

// Properties
val tornado_version: String by project
val kotlin_version: String by project
val json_version: String by project
val junit4_version: String by project
val junit5_version: String by project
val testfx_version: String by project
val hamcrest_version: String by project

group = "no.tornado"
version = "2.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

// Main Dependencies
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlin_version}")
    implementation("org.glassfish:javax.json:${json_version}")
    implementation("org.apache.httpcomponents:httpclient:4.5.3")
    implementation("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
    implementation("org.apache.felix:org.apache.felix.framework:6.0.1")
}

// JavaFX Configuration
javafx {
    version = "22"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.swing", "javafx.web", "javafx.media")
}

// Java Configuration
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

// Test Configuration
sourceSets {
    test {
        java {
            setSrcDirs(listOf("src/test/kotlin"))
        }
    }
}

// Test Dependencies
dependencies {
    // JUnit 4
    testImplementation("junit:junit:${junit4_version}")
    
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine") // This allows running JUnit 4 tests
    
    // Kotlin Test
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${kotlin_version}") // JUnit 4 support
    
    // Testing Libraries
    testImplementation("org.hamcrest:hamcrest:${hamcrest_version}")
    testImplementation("org.hamcrest:hamcrest-library:${hamcrest_version}")
    testImplementation("org.assertj:assertj-core:3.24.2")
    
    // TestFX
    testImplementation("org.testfx:testfx-core:${testfx_version}")
    testImplementation("org.testfx:testfx-junit5:${testfx_version}") // Changed from testfx-junit to testfx-junit5
    testImplementation("org.testfx:testfx-junit:4.0.15-alpha") // Add specific version for JUnit 4 support
    
    // HTTP and JSON for tests
    testImplementation("javax.json:javax.json-api:1.1.4")
    testImplementation("org.glassfish:javax.json:${json_version}")
    
    // Headless Testing
    testRuntimeOnly("org.testfx:openjfx-monocle:jdk-12.0.1+2")
}

kotlin {
    jvmToolchain(17)
}

/*
// Kotlin Configuration
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}
 */
// Skip tests and test compilation by default
gradle.startParameter.excludedTaskNames.addAll(listOf("test", "compileTestKotlin", "compileTestJava"))

// Create a separate task for running tests
tasks.register<Test>("runTests") {
    // Enable test compilation for this task
    tasks.compileTestKotlin.get().enabled = true
    tasks.compileTestJava.get().enabled = true
    
    useJUnit() // Use JUnit 4
    
    testLogging {
        events("passed", "skipped", "failed")
    }

    systemProperty("java.module.path", "")
    
    jvmArgs = listOf(
        "--add-opens=javafx.graphics/com.sun.javafx.application=ALL-UNNAMED",
        "--add-opens=javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED",
        "--add-opens=javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED",
        "--add-opens=javafx.base/com.sun.javafx.binding=ALL-UNNAMED",
        "--add-opens=javafx.base/com.sun.javafx.event=ALL-UNNAMED",
        "--add-opens=javafx.base/com.sun.javafx.runtime=ALL-UNNAMED",
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.util=ALL-UNNAMED",
        "--add-exports=javafx.graphics/com.sun.javafx.application=ALL-UNNAMED",
        "--add-exports=javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED",
        "--illegal-access=permit"
    )
}

// Disable test tasks
tasks.test {
    enabled = false
}

tasks.compileTestKotlin {
    enabled = false
}

tasks.compileTestJava {
    enabled = false
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}