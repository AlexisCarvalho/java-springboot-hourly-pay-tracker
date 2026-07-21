plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.dependency.management)
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
	implementation(libs.spring.web)
	implementation(libs.spring.data.jpa)
	implementation(libs.spring.validation)
	implementation(libs.spring.security)

	implementation(libs.mapstruct)
	annotationProcessor(libs.mapstruct.processor)

	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)
	annotationProcessor(libs.lombok.mapstruct.binding)

	implementation(libs.postgresql)

	implementation(libs.jjwt.api)
	runtimeOnly(libs.jjwt.impl)
	runtimeOnly(libs.jjwt.jackson)

	testImplementation(libs.spring.test)
	testRuntimeOnly(libs.junit.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<JavaCompile> {
	options.compilerArgs.add("-parameters")
}
