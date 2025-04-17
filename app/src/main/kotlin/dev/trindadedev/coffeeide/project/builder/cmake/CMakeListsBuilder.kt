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

fun CMakeLists(
  autoLineBreak: Boolean = true,
  block: CMakeListsBuilder.() -> Unit,
): CMakeListsBuilder {
  val builder = CMakeListsBuilder()
  builder.autoLineBreak = autoLineBreak
  builder.block()
  return builder
}

fun CMakeListsBuilder.cmakeMinimumRequired(version: String) {
  add("cmake_minimum_required(VERSION $version)")
  if (autoLineBreak) lineBreak()
}

fun CMakeListsBuilder.set(variable: String, value: String) {
  add("set($variable $value)")
  if (autoLineBreak) lineBreak()
}

fun CMakeListsBuilder.project(nameVar: String) {
  add("project(\${$nameVar})")
  if (autoLineBreak) lineBreak()
}

fun CMakeListsBuilder.addExecutable(targetVar: String, sourcesVar: String) {
  add("add_executable(\${$targetVar} \${$sourcesVar})")
  if (autoLineBreak) lineBreak()
}

fun CMakeListsBuilder.comment(comment: String) {
  add("# ${comment}")
  if (autoLineBreak) lineBreak()
}

fun CMakeListsBuilder.lineBreak() {
  add("\n")
}

class CMakeListsBuilder {
  val codeBuilder = StringBuilder()
  var autoLineBreak = true

  fun add(text: String) {
    codeBuilder.append(text)
  }

  fun asText(): String = codeBuilder.toString()
}
