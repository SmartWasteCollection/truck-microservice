plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.0.0"
    id("org.springframework.boot") version "2.7.7"
    jacoco
    application
}

repositories {
    mavenCentral()
}

val kotestVersion: String by project

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.7")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.azure:azure-digitaltwins-core:1.3.4")
    implementation("com.azure:azure-identity:1.5.3")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.0")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

application {
    mainClass.set("swc.microservice.truck.TruckMicroserviceKt")
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}
