package dev.trindadedev.coffeeide.ui.screens.editor

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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.trindadedev.coffeeide.project.manage.ProjectManager
import dev.trindadedev.coffeeide.ui.components.appbar.CoffeeTopAppBar
import java.io.File

@Composable
fun EditorScreen(projectPath: String) {
  val project = ProjectManager.instance.readFromPath(File(projectPath))
  val projectName = project?.name ?: "Ghost Project"
  Scaffold(
    modifier = Modifier.imePadding(),
    topBar = {
      CoffeeTopAppBar(
        title = { Text(text = projectName) },
      )
    },
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      Text(text = projectName)
      Text(text = projectPath)
    }
  }
}