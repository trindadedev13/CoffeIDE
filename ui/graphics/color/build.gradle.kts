plugins {
  alias(libs.plugins.coffeide.library)
  alias(libs.plugins.coffeide.library.compose)
}

android {
  namespace = "dev.trindadedev.coffeide.ui.graphics.color"

  buildFeatures {
    viewBinding = true
    compose = true
  }
}

dependencies {
  implementation(libs.bundles.compose)
}