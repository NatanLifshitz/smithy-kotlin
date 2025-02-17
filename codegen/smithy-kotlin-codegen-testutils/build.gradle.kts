/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */
import aws.sdk.kotlin.gradle.dsl.configurePublishing
plugins {
    kotlin("jvm")
    jacoco
    `maven-publish`
}

description = "Provides common test utilities for Smithy-Kotlin code generation"
extra["displayName"] = "Smithy :: Kotlin :: Codegen Utils"
extra["moduleName"] = "software.amazon.smithy.kotlin.codegen.test"

val sdkVersion: String by project
group = "software.amazon.smithy.kotlin"
version = sdkVersion

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.smithy.aws.traits)
    api(project(":codegen:smithy-kotlin-codegen"))

    // Test dependencies
    implementation(libs.junit.jupiter)
    implementation(libs.kotest.assertions.core.jvm)
    implementation(libs.kotlin.test)
    implementation(libs.kotlin.test.junit5)
}

// unlike the runtime, smithy-kotlin codegen package is not expected to run on Android...we can target 1.8
tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

// Reusable license copySpec
val licenseSpec = copySpec {
    from("${project.rootDir}/LICENSE")
    from("${project.rootDir}/NOTICE")
}

// Configure jars to include license related info
tasks.jar {
    metaInf.with(licenseSpec)
    inputs.property("moduleName", project.name)
    manifest {
        attributes["Automatic-Module-Name"] = project.name
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    group = "publishing"
    description = "Assembles Kotlin sources jar"
    classifier = "sources"
    from(sourceSets.getByName("main").allSource)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        showStackTraces = true
        showExceptions = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

publishing {
    publications {
        create<MavenPublication>("codegen-testutils") {
            from(components["java"])
            artifact(sourcesJar)
        }
    }
}

configurePublishing("smithy-kotlin")
