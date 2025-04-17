plugins {
  alias(libs.plugins.coffeeide.library)
  alias(libs.plugins.coffeeide.library.compose)
}

android {
  namespace = "dev.trindadedev.coffeeide.ui.graphics.color"

  buildFeatures {
    viewBinding = true
    compose = true
  }
}

dependencies {
  implementation(libs.bundles.compose)
}