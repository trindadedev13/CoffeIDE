package dev.trindadedev.coffeide.ui.base

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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.trindadedev.coffeide.ui.components.toast.LocalToastHostState
import dev.trindadedev.coffeide.ui.components.toast.ToastHost
import dev.trindadedev.coffeide.ui.components.toast.rememberToastHostState
import dev.trindadedev.coffeide.ui.platform.LocalMainNavController
import dev.trindadedev.coffeide.ui.theme.CoffeIDETheme

abstract class BaseComponentActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    setContent {
      CoffeIDETheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          ProvideMainCompositionLocals {
            onScreenCreated(savedInstanceState)
            ToastHost()
          }
        }
      }
    }
  }

  @Composable
  public abstract fun onScreenCreated(savedInstanceState: Bundle?)

  @Composable
  private fun ProvideMainCompositionLocals(content: @Composable () -> Unit) {
    val navController = rememberNavController()
    val toastHostState = rememberToastHostState()
    CompositionLocalProvider(
      LocalMainNavController provides navController,
      LocalToastHostState provides toastHostState,
      content = content
    )
  }
}
