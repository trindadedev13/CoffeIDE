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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.trindadedev.coffeeide.Strings
import dev.trindadedev.coffeeide.navigation.routes.EditorRoute
import dev.trindadedev.coffeeide.ui.components.appbar.CoffeeTopAppBar
import dev.trindadedev.coffeeide.ui.components.toast.LocalToastHostState
import dev.trindadedev.coffeeide.ui.platform.LocalMainNavController
import dev.trindadedev.coffeeide.ui.screens.home.project.create.CreateProjectDialog
import dev.trindadedev.coffeeide.ui.screens.home.project.create.CreateProjectViewModel
import dev.trindadedev.coffeeide.ui.screens.home.project.list.ProjectsList
import dev.trindadedev.coffeeide.ui.screens.home.project.list.ProjectsListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  viewModel: HomeViewModel = viewModel(),
  createProjectViewModel: CreateProjectViewModel = viewModel(),
  projectsListViewModel: ProjectsListViewModel = viewModel(),
) {
  val context = LocalContext.current
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
  val coroutineScope = rememberCoroutineScope()
  val toastHostState = LocalToastHostState.current
  val lazyListState = rememberLazyListState()
  val fabVisible by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset <= 10 } }
  val navController = LocalMainNavController.current

  Scaffold(
    modifier =
      Modifier.navigationBarsPadding()
        .imePadding()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      CoffeeTopAppBar(
        title = { Text(text = stringResource(id = Strings.app_name)) },
        scrollBehavior = scrollBehavior,
        isLarge = true,
      )
    },
    floatingActionButton = {
      AnimatedVisibility(
        visible = fabVisible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
      ) {
        ExtendedFloatingActionButton(
          onClick = { viewModel.openCreateProjectDialog() },
          icon = {
            Icon(
              Icons.Filled.Add,
              contentDescription = stringResource(id = Strings.text_new_project),
            )
          },
          text = { Text(text = stringResource(id = Strings.text_new_project)) },
        )
      }
    },
  ) { innerPadding ->
    ProjectsList(
      modifier =
        Modifier.padding(innerPadding)
          .fillMaxSize()
          .nestedScroll(scrollBehavior.nestedScrollConnection),
      lazyListState = lazyListState,
      viewModel = projectsListViewModel,
      onProjectClick = { project ->
        project.path?.let {
          navController.navigate(EditorRoute(it.absolutePath))
        }
      },
    )

    if (viewModel.uiState.isCreateProjectDialogOpen) {
      CreateProjectDialog(
        viewModel = createProjectViewModel,
        onClose = {
          viewModel.closeCreateProjectDialog()
          viewModel.clear(createProjectViewModel, projectsListViewModel)
        },
        onCreate = { viewModel.createProject(createProjectViewModel, projectsListViewModel) },
        onError = {
          coroutineScope.launch {
            toastHostState.showToast(
              message = context.getString(Strings.text_error_project_name),
              icon = Icons.Filled.Error,
            )
          }
        },
      )
    }
  }
}
