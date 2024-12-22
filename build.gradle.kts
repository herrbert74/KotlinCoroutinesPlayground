import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "2.1.0"
	application
}

group = "me.zsoltbertalan"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.0")
	testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.0")
	testImplementation("io.mockk:mockk:1.13.14")
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	jvmToolchain(21)
}

application {
	mainClass.set("MainKt")
}

tasks.withType<KotlinCompile>().configureEach {
	compilerOptions.freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
}

tasks.withType<KotlinCompile>().configureEach {
	compilerOptions.freeCompilerArgs.add("-opt-in=kotlinx.coroutines.DelicateCoroutinesApi")
}
