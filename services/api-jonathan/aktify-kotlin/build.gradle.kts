import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    kotlin("jvm") version("1.4.10")
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

application {
    mainClassName = "ninja.jwillis.service.ServiceKt"
}

group = "ninja.jwillis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

val vertxVersion = "3.8.5"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation(kotlin("reflect"))

    implementation("me.koddle:Koddle:1.0-SNAPSHOT")
    implementation("commons-codec:commons-codec:1.13")

    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-web-api-contract:$vertxVersion")
    implementation("io.vertx:vertx-web-client:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
    implementation("io.vertx:vertx-unit:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
    implementation("io.vertx:vertx-pg-client:$vertxVersion")
    implementation("io.vertx:vertx-config:$vertxVersion")
    implementation("io.vertx:vertx-auth-jwt:$vertxVersion")

    implementation("com.github.kittinunf.fuel:fuel:2.2.0")
    implementation("postgresql:postgresql:9.1-901-1.jdbc4")
    implementation("org.koin:koin-core:2.0.1")
    implementation("org.koin:koin-core-ext:2.0.1")
    implementation("org.slf4j:slf4j-jdk14:1.7.28")

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.0")
    testImplementation("org.koin:koin-test:2.0.1")
}

tasks {
    test {
        useJUnitPlatform()
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    register("getHomeDir")  {
        doLast{
            println(gradle.gradleHomeDir)
        }
    }
    withType<ShadowJar> {
        archiveBaseName.set("aktify")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}
