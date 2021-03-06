/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.api.scan.filesystem;

import org.sonar.api.BatchComponent;

import javax.annotation.CheckForNull;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @since 3.5
 */
public interface ModuleFileSystem extends BatchComponent {

  /**
   * Base directory.
   */
  File baseDir();

  /**
   * Optional directory used by the build tool to generate various kinds of data (test reports, temp files, ...).
   * In Maven, it's given by the property ${project.build.directory}, which value is generally ${project.basedir}/target.
   */
  @CheckForNull
  File buildDir();

  /**
   * Source directories. Non-existing directories are excluded.
   * Example in Maven : ${project.basedir}/src/main/java
   * @deprecated since 4.2 will always return {@link #baseDir()}
   */
  @Deprecated
  List<File> sourceDirs();

  /**
   * Test directories. Non-existing directories are excluded.
   * Example in Maven : ${project.basedir}/src/test/java
   * @deprecated since 4.2 will always return {@link #baseDir()}
   */
  @Deprecated
  List<File> testDirs();

  /**
   * Optional directories that contain the compiled sources, for example java bytecode.
   * Note that :
   * <ul>
   * <li>Maven projects have only a single binary directory, which is generally ${project.basedir}/target/classes</li>
   * <li>Binary directories can be empty</li>
   * <li>Test binary directories are not supported yet.</li>
   * </ul>
   * @deprecated since 4.2 sonar.binaries should be converted to language specific property
   */
  @Deprecated
  List<File> binaryDirs();

  /**
   * Search for files. Never return null.
   * @deprecated since 4.2 use {@link #inputFiles(FileQuery)}
   */
  @Deprecated
  List<File> files(FileQuery query);

  /**
   * Search for input files. Never return null.
   * @since 4.2
   */
  Iterable<InputFile> inputFiles(FileQuery query);

  /**
   * Search for input file corresponding to the given java.io.File.
   * @since 4.2
   */
  @CheckForNull
  InputFile inputFile(File ioFile);

  /**
   * Search for input directory corresponding to the given java.io.File.
   * @since 4.2
   */
  @CheckForNull
  InputDir inputDir(File ioFile);

  /**
   * Default charset for files of the module. If it's not defined, then
   * return the platform default charset. When trying to read an input file it is better to rely on
   * {@link InputFile#encoding()} as encoding may be different for each file.
   */
  Charset sourceCharset();

  /**
   * Working directory used by Sonar. This directory can be used for example to
   * store intermediary reports.
   */
  File workingDir();
}
