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
    finalizedBy("jacocoTestReport") // test 작업 후에 jacocoTestReport 작업 실행
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

tasks.named("jacocoTestCoverageVerification", JacocoCoverageVerification::class.java) {
    dependsOn(tasks.jacocoTestReport)
    violationRules {
        rule {
            element = "CLASS"

            // 라인 커버리지 기준
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal() // 최소 라인 커버리지 기준 설정
            }

            // 메소드 커버리지 기준
            limit {
                counter = "METHOD"
                value = "COVEREDRATIO"
                minimum = "1.00".toBigDecimal() // 최소 메소드 커버리지 기준 설정
            }

            // 분기 커버리지 기준
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal() // 최소 분기 커버리지 기준 설정
            }

            // 명령어 커버리지 기준
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal() // 최소 명령어 커버리지 기준 설정
            }
        }
    }
}











