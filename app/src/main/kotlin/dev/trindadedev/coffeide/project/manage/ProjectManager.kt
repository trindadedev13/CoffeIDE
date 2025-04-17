package dev.trindadedev.coffeide.project.manage

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

import dev.trindadedev.coffeide.Paths
import dev.trindadedev.coffeide.coffeProjectsPath
import dev.trindadedev.coffeide.project.Project
import dev.trindadedev.coffeide.project.builder.cmake.CMakeLists
import dev.trindadedev.coffeide.project.builder.cmake.comment
import dev.trindadedev.coffeide.project.builder.cmake.set
import dev.trindadedev.coffeide.project.builder.cmake.cmakeMinimumRequired
import dev.trindadedev.coffeide.project.builder.cmake.project
import dev.trindadedev.coffeide.project.builder.cmake.addExecutable
import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class ProjectManager private constructor() {

  companion object {
    @JvmStatic
    val instance: ProjectManager by lazy { ProjectManager() }
  }

  fun create(project: Project) {
    val fileProject = File(Paths.coffeProjectsPath, project.name)
    if (!fileProject.exists()) {
      fileProject.mkdirs()
    }
    val fileProjectMainC = File(fileProject, "main.c")
    val contentProjectMainC = """
    #include <stdio.h>

    int main() {
      printf("Hello, World!");
      printf("\n");
      printf("Powered by Coffe IDE 2025 by Aquiles Trindade.");
      return 0;
    }
    """
    if (!fileProjectMainC.exists()) {
      fileProjectMainC.createNewFile()
      fileProjectMainC.writeText(contentProjectMainC)
    }
    val fileProjectCMakeLists = File(fileProject, "CMakeLists.txt")
    val contentProjectCMakeLists = CMakeLists {
      comment("Basic CMake config")
      comment("Powered by Coffe IDE by Aquiles Trindade")
      cmakeMinimumRequired("3.10")

      set("PROJECT_NAME", project.name ?: "Ghost Project")
      comment("You can add your files here.")
      set("SOURCE_FILES", "main.c")

      project("PROJECT_NAME")
      addExecutable("PROJECT_NAME", "SOURCE_FILES")
    }.asText()
    if (!fileProjectCMakeLists.exists()) {
      fileProjectCMakeLists.createNewFile()
      fileProjectCMakeLists.writeText(contentProjectCMakeLists)
    }
  }

  fun readFromPath(path: File): Project? {
    if (path.exists()) {
      val json = path.readText()
      val project = Json.decodeFromString<Project>(json)
    }
    return null
  }
}