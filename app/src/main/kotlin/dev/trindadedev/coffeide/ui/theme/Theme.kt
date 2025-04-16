package dev.trindadedev.coffeide.ui.theme

/*
 * Copyright 2025 Coffe-IDE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.kyant.monet.LocalTonalPalettes
import com.kyant.monet.PaletteStyle
import com.kyant.monet.TonalPalettes.Companion.toTonalPalettes
import com.kyant.monet.dynamicColorScheme

@Composable
fun CoffeIDETheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  highContrastDarkTheme: Boolean = false,
  dynamicColor: Boolean = true,
  seedColor: Color = Color(0xFF2D140D),
  content: @Composable () -> Unit,
) {
  val brownTonalPalettes = seedColor.toTonalPalettes(style = PaletteStyle.Vibrant)

  CompositionLocalProvider(LocalTonalPalettes provides brownTonalPalettes) {
    val colorScheme =
      dynamicColorScheme(!darkTheme).run {
        if (highContrastDarkTheme && darkTheme)
          copy(
            surface = Color.Black,
            background = Color.Black,
            surfaceContainerLowest = Color.Black,
            surfaceContainerLow = surfaceContainerLowest,
            surfaceContainer = surfaceContainerLow,
            surfaceContainerHigh = surfaceContainerLow,
            surfaceContainerHighest = surfaceContainer,
          )
        else this
      }

    val view = LocalView.current
    if (!view.isInEditMode) {
      SideEffect {
        (view.context as Activity).apply {
          WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = !darkTheme
            isAppearanceLightNavigationBars = !darkTheme
          }
        }
      }
    }

    MaterialTheme(
      colorScheme = colorScheme,
      content = content
    )
  }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S