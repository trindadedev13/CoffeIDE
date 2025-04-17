package dev.trindadedev.coffeeide.ui.screens.home

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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.trindadedev.coffeeide.project.Project
import dev.trindadedev.coffeeide.project.manage.ProjectManager
import dev.trindadedev.coffeeide.ui.screens.home.project.create.CreateProjectViewModel
import dev.trindadedev.coffeeide.ui.screens.home.project.list.ProjectsListViewModel

class HomeViewModel: ViewModel() {
  private var _uiState by mutableStateOf(HomeUIState())
  val uiState: HomeUIState
    get() = _uiState

  fun openCreateProjectDialog() {
    _uiState = _uiState.copy(isCreateProjectDialogOpen = true)
  }

  fun closeCreateProjectDialog() {
    _uiState = _uiState.copy(isCreateProjectDialogOpen = false)
  }

  fun createProject(
    createProjectViewModel: CreateProjectViewModel,
    projectsListViewModel: ProjectsListViewModel
  ) {
    val project = Project(name = createProjectViewModel.uiState.projectName)
    ProjectManager.instance.create(project)
    closeCreateProjectDialog()
    projectsListViewModel.loadProjects()
    createProjectViewModel.setProjectName("")
  }
}