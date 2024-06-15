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
    testImplementation("org.mockito:mockito-core:3.+")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // test 작업 후에 jacocoTestReport 작업 실행
    ignoreFailures = true // 테스트 실패를 무시하고 계속 진행
    reports {
        junitXml.required.set(true) // JUnit XML 보고서 생성
        html.required.set(true) // HTML 보고서 생성
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(file("${buildDir}/reports/jacoco/test/html"))
    }
}

tasks.register<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn(tasks.jacocoTestReport)

    violationRules {
        rule {
            element = "CLASS"
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal() // 최소 커버리지 기준 설정
            }
        }
    }
}












