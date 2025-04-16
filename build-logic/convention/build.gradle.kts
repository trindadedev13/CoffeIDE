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
    
    register("coffeide.application.compose") {
      id = "coffeide.application.compose"
      implementationClass = "ApplicationComposeConventionPlugin"
    }

    register("coffeide.library.compose") {
      id = "coffeide.library.compose"
      implementationClass = "LibraryComposeConventionPlugin"
    }
  }
}