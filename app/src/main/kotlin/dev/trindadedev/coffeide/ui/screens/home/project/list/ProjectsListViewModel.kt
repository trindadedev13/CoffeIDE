package dev.trindadedev.coffeide.ui.screens.home.project.list

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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.trindadedev.coffeide.Paths
import dev.trindadedev.coffeide.coffeProjectsPath
import dev.trindadedev.coffeide.project.Project

class ProjectsListViewModel: ViewModel() {
  private var _uiState by mutableStateOf(ProjectsListUIState())
  val uiState: ProjectsListUIState
    get() = _uiState

  init {
    loadProjects()
  }

  fun loadProjects() {
    if (!Paths.coffeProjectsPath.exists()) Paths.coffeProjectsPath.mkdirs()
    val projectsAsFiles = Paths.coffeProjectsPath.listFiles { it.isDirectory }
    val newProjects = projectsAsFiles?.map { Project(it.name) } ?: emptyList()
    _uiState = _uiState.copy(projects = newProjects)
  }
}