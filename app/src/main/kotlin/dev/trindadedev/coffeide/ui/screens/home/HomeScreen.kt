package dev.trindadedev.coffeide.ui.screens.home

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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.trindadedev.coffeide.Strings
import dev.trindadedev.coffeide.project.Project
import dev.trindadedev.coffeide.project.manage.ProjectManager
import dev.trindadedev.coffeide.ui.components.appbar.CoffeTopAppBar
import dev.trindadedev.coffeide.ui.components.toast.LocalToastHostState
import dev.trindadedev.coffeide.ui.screens.home.project.create.CreateProjectViewModel
import dev.trindadedev.coffeide.ui.screens.home.project.create.CreateProjectDialog
import dev.trindadedev.coffeide.ui.screens.home.project.list.ProjectsList
import dev.trindadedev.coffeide.ui.screens.home.project.list.ProjectsListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  viewModel: HomeViewModel = viewModel(),
  createProjectViewModel: CreateProjectViewModel = viewModel(),
  projectsListViewModel: ProjectsListViewModel = viewModel()
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  val scrollState = rememberScrollState()
  val coroutineScope = rememberCoroutineScope()
  val toastHostState = LocalToastHostState.current

  Scaffold(
    modifier = Modifier
      .navigationBarsPadding()
      .imePadding()
      .nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      CoffeTopAppBar(
        title = { Text(text = stringResource(id = Strings.app_name)) },
        scrollBehavior = scrollBehavior,
        isLarge = true,
      )
    },
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = { viewModel.openCreateProjectDialog() },
        icon = { Icon(Icons.Filled.Add, contentDescription = stringResource(id = Strings.text_new_project)) },
        text = { Text(text = stringResource(id = Strings.text_new_project)) },
      )
    },
  ) { innerPadding ->
    ProjectsList(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize(),
      viewModel = projectsListViewModel,
      onProjectClick = { project ->
        coroutineScope.launch {
          toastHostState.showToast(
            message = "Editor not implemented yet.",
            icon = Icons.Filled.Error,
          )
        }
      }
    )

    if (viewModel.uiState.isCreateProjectDialogOpen) {
      CreateProjectDialog(
        viewModel = createProjectViewModel,
        onClose = { viewModel.closeCreateProjectDialog() },
        onCreate = {
          val project = Project(name = createProjectViewModel.uiState.projectName)
          ProjectManager.instance.create(project)
          viewModel.closeCreateProjectDialog()
          projectsListViewModel.loadProjects()
          createProjectViewModel.setProjectName("")
        }
      )
    }
  }
}
