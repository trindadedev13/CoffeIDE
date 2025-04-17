package dev.trindadedev.coffeeide.project.manage

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

import dev.trindadedev.coffeeide.Paths
import dev.trindadedev.coffeeide.coffeeProjectsPath
import dev.trindadedev.coffeeide.project.Project
import dev.trindadedev.coffeeide.project.builder.cmake.CMakeLists
import dev.trindadedev.coffeeide.project.builder.cmake.comment
import dev.trindadedev.coffeeide.project.builder.cmake.set
import dev.trindadedev.coffeeide.project.builder.cmake.cmakeMinimumRequired
import dev.trindadedev.coffeeide.project.builder.cmake.project
import dev.trindadedev.coffeeide.project.builder.cmake.addExecutable
import dev.trindadedev.coffeeide.project.builder.cmake.lineBreak
import java.io.File

class ProjectManager private constructor() {

  companion object {
    @JvmStatic
    val instance: ProjectManager by lazy { ProjectManager() }
  }

  fun create(project: Project) {
    val fileProject = File(Paths.coffeeProjectsPath, project.name)
    if (!fileProject.exists()) {
      fileProject.mkdirs()
    }
    val fileProjectMainC = File(fileProject, "main.c")
    val contentProjectMainC = """
    #include <stdio.h>

    int main() {
      printf("Hello, World!");
      printf("\n");
      printf("Powered by Coffee IDE 2025 by Aquiles Trindade.");
      return 0;
    }
    """.trimIndent()
    if (!fileProjectMainC.exists()) {
      fileProjectMainC.createNewFile()
      fileProjectMainC.writeText(contentProjectMainC)
    }
    val fileProjectCMakeLists = File(fileProject, "CMakeLists.txt")
    val contentProjectCMakeLists = CMakeLists {
      comment("Basic CMake config")
      comment("Powered by Coffee IDE by Aquiles Trindade")
      lineBreak()

      cmakeMinimumRequired("3.10")
      lineBreak()

      set("PROJECT_NAME", project.name ?: "Ghost Project")
      lineBreak()

      comment("You can add your files here.")
      set("SOURCE_FILES", "main.c")
      lineBreak()

      project("PROJECT_NAME")
      lineBreak()

      addExecutable("PROJECT_NAME", "SOURCE_FILES")
    }.asText()
    if (!fileProjectCMakeLists.exists()) {
      fileProjectCMakeLists.createNewFile()
      fileProjectCMakeLists.writeText(contentProjectCMakeLists)
    }
  }

  fun readFromPath(path: File): Project? {
    if (path.exists()) {
      return Project(name = path.name)
    }
    return null
  }
}