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
    register("coffeeide.application") {
      id = "coffeeide.application"
      implementationClass = "ApplicationConventionPlugin"
    }
    
    register("coffeeide.library") {
      id = "coffeeide.library"
      implementationClass = "LibraryConventionPlugin"
    }
    
    register("coffeeide.application.compose") {
      id = "coffeeide.application.compose"
      implementationClass = "ApplicationComposeConventionPlugin"
    }

    register("coffeeide.library.compose") {
      id = "coffeeide.library.compose"
      implementationClass = "LibraryComposeConventionPlugin"
    }
  }
}