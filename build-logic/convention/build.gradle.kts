plugins {
  `kotlin-dsl`
}

dependencies {
  compileOnly(libs.android.gradle)
  compileOnly(libs.compose.gradle)
  compileOnly(libs.kotlin.gradle)
  compileOnly(libs.ksp.gradle)
}

gradlePlugin {
  plugins {
    register("coffeide.application") {
      id = "coffeide.application"
      implementationClass = "ApplicationConventionPlugin"
    }
    
    register("coffeide.library") {
      id = "coffeide.library"
      implementationClass = "LibraryConventionPlugin"
    }
    
    register("coffeide.compose") {
      id = "coffeide.compose"
      implementationClass = "ComposeConventionPlugin"
    }
  }
}