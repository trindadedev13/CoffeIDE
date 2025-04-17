package dev.trindadedev.coffeeide.ui.screens.home.project.create

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.trindadedev.coffeeide.Strings
import dev.trindadedev.coffeeide.ui.components.dialog.EnhancedAlertDialog
import dev.trindadedev.coffeeide.ui.theme.OutlinedTextFieldShape

@Composable
fun CreateProjectDialog(
  modifier: Modifier = Modifier,
  viewModel: CreateProjectViewModel,
  onClose: () -> Unit,
  onCreate: () -> Unit,
  onError: () -> Unit,
) {
  val uiState = viewModel.uiState
  CreateProjectDialogImpl(
    modifier = modifier,
    viewModel = viewModel,
    onClose = onClose,
    onCreate = onCreate,
    onError = onError,
  )
}

@Composable
private fun CreateProjectDialogImpl(
  modifier: Modifier = Modifier,
  viewModel: CreateProjectViewModel,
  onClose: () -> Unit,
  onCreate: () -> Unit,
  onError: () -> Unit,
) {
  EnhancedAlertDialog(
    modifier = modifier,
    onDismissRequest = onClose,
    title = {
      Text(text = stringResource(id = Strings.text_new_project))
    },
    text = {
      Column {
        OutlinedTextField(
          value = viewModel.uiState.projectName,
          onValueChange = { viewModel.setProjectName(it) },
          label = { Text(text = stringResource(id = Strings.text_project_name)) },
          placeholder = { Text(text = "My Project") },
          singleLine = true,
          modifier = Modifier
              .fillMaxWidth(),
          isError = viewModel.uiState.isError,
          shape = OutlinedTextFieldShape
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (!viewModel.uiState.isError) onCreate() else onError()
        }
      ) {
        Text(text = stringResource(id = Strings.text_create))
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onClose) {
        Text(text = stringResource(id = Strings.text_cancel))
      }
    },
  )
}