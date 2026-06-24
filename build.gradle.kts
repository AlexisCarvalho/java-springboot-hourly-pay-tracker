plugins {
	java
	id("org.springframework.boot") version "4.0.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.alexis"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// MapStruct
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	// JPA for Annotations and Hibernate (@Entity, @Table...)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	// Validation for Annotations (@NotNull, @Size...)
	implementation("org.springframework.boot:spring-boot-starter-validation")
	// Lombok
	compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	// Postgre
    implementation("org.postgresql:postgresql:42.7.3") 
	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	// Jackson for JSON serialization 
	implementation("com.fasterxml.jackson.core:jackson-databind")
	// Jackson JSR310 for Java 8 date/time support
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	// Spring Security 
	implementation("org.springframework.boot:spring-boot-starter-security")

    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaCompile> {
	options.compilerArgs.add("-parameters")
}
