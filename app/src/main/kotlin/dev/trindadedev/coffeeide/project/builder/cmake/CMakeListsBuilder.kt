package dev.trindadedev.coffeeide.project.builder.cmake

/*
 * Copyright 2025 Coffee-IDE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

fun CMakeLists(block: CMakeListsBuilder.() -> Unit): CMakeListsBuilder {
  val builder = CMakeListsBuilder()
  builder.block()
  return builder
}

fun CMakeListsBuilder.cmakeMinimumRequired(version: String) {
  addLine("cmake_minimum_required(VERSION $version)")
}

fun CMakeListsBuilder.set(variable: String, value: String) {
  addLine("set($variable $value)")
}

fun CMakeListsBuilder.project(nameVar: String) {
  addLine("project(\${$nameVar})")
}

fun CMakeListsBuilder.addExecutable(targetVar: String, sourcesVar: String) {
  addLine("add_executable(\${$targetVar} \${$sourcesVar})")
}

fun CMakeListsBuilder.comment(comment: String) {
  addLine("# ${comment}")
}

fun CMakeListsBuilder.lineBreak() {
  addLine("\n")
}

class CMakeListsBuilder {
  private val lines = mutableListOf<String>()

  fun addLine(line: String) {
    lines += line
  }

  fun asText(): String = lines.joinToString("\n")
}