package dev.trindadedev.coffeeide.ui.screens.home.project.list

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.trindadedev.coffeeide.project.Project

@Composable
fun ProjectsList(
  modifier: Modifier = Modifier.fillMaxHeight(),
  lazyListState: LazyListState = rememberLazyListState(),
  viewModel: ProjectsListViewModel = viewModel(),
  onProjectClick: (Project) -> Unit
) {
  LazyColumn(
    state = lazyListState,
    modifier = modifier
  ) {
    items(viewModel.uiState.projects) { project ->
      Card(
        modifier = Modifier
          .padding(
            start = 8.dp,
            end = 8.dp,
            top = 6.dp,
            bottom = 6.dp,
          )
          .fillMaxWidth(),
        onClick = { onProjectClick(project) }
      ) {
        Text(
          modifier = Modifier
            .padding(
              start = 10.dp,
              end = 10.dp,
              top = 14.dp,
              bottom = 14.dp,
            ),
          text = project.name ?: "Ghost Project"
        )
      }
    }
  }
}