package org.lifesync.dateswap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileCopier {
  final Path sourcePath;

  public FileCopier(final Path sourcePath, final Path destinationPath) {
    this.sourcePath = sourcePath;
  }

  public void copyFilesFrom() {
    try (Stream<Path> paths = Files.walk(sourcePath)) {
      paths
          .filter(Files::isRegularFile)
          // Just print out the paths to ensure the files are actually being read right.
          // Then need to change to copy method below and pass in source & destination paths.
          .forEach(System.out::println);
    } catch (IOException ioe) {
      throw new RuntimeException(
          "Something went wrong when reading in the files before copying.", ioe);
    }
  }

  /**
   * Copies the file at source to a new file at the destination.
   *
   * @param source The path of the file to copy.
   * @param destination The path of the copied file to create.
   */
  protected static void copy(final Path source, final Path destination) {
    if (Files.exists(destination)) {
      return;
    }

    try {
      Files.copy(
          source,
          destination,
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.COPY_ATTRIBUTES);
    } catch (UnsupportedOperationException e) {
      throw new RuntimeException("Invalid copy option. (Shouldn't be thrown).", e);
    } catch (IOException ioe) {
      throw new RuntimeException("Something went wrong when copying the files.", ioe);
    }
  }
}
