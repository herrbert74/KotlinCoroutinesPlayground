import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.9.23"
	application
}

group = "me.zsoltbertalan"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
	testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.21")
	testImplementation("io.mockk:mockk:1.13.9")
}

tasks.test {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

application {
	mainClass.set("MainKt")
}

tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
}

tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.coroutines.DelicateCoroutinesApi"
}
