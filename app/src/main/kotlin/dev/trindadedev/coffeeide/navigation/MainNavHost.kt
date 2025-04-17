package dev.trindadedev.coffeeide.navigation

/*
 * Copyright 2025 Coffee-IDE.
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

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import dev.trindadedev.coffeeide.navigation.routes.EditorRoute
import dev.trindadedev.coffeeide.navigation.routes.HomeRoute
import dev.trindadedev.coffeeide.ui.platform.LocalMainNavController
import dev.trindadedev.coffeeide.ui.screens.editor.EditorScreen
import dev.trindadedev.coffeeide.ui.screens.home.HomeScreen

@Composable
fun MainNavHost() {
  val navController = LocalMainNavController.current

  BaseNavHost(navController = navController, startDestination = HomeRoute) {
    composable<HomeRoute> { HomeScreen() }
    composable<EditorRoute> { EditorScreen() }
  }
}
