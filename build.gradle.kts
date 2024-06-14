plugins {
    id("java")
    id("jacoco")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:4.3.1")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport") // test 작업 후에 jacocoTestReport 작업 실행
    ignoreFailures = true // 테스트 실패를 무시하고 계속 진행
}

tasks.named("jacocoTestReport", JacocoReport::class.java) {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(file("${buildDir}/reports/jacoco/test/html"))
    }
}













