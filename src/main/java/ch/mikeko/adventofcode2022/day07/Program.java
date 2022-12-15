package ch.mikeko.adventofcode2022.day07;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(7, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            var rootDirectory = new Directory(null, "/");

            buildDirectoryTree(allLines, rootDirectory);

            var sumOfQualifiedDirectories = walkDirectoryTree(rootDirectory);

            System.out.printf("Part one result: %s%n", sumOfQualifiedDirectories);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void buildDirectoryTree(List<String> allLines, Directory rootDirectory) {
        Directory currentDirectory = null;
        for (var line : allLines) {
            if (line.startsWith("$")) {
                //region Commands
                // Change Directory
                if (line.startsWith("$ cd")) {
                    var targetDirectory = line.replace("$ cd ", "");
                    if ("/".equals(targetDirectory)) {
                        currentDirectory = rootDirectory;
                    } else if ("..".equals(targetDirectory)) {
                        currentDirectory = currentDirectory
                                .getParentDirectory()
                                .orElseThrow(() -> new RuntimeException("Attempted to cd out of the root directory"));
                    } else {
                        currentDirectory = currentDirectory
                                .getSubdirectories()
                                .stream()
                                .filter(x -> targetDirectory.equals(x.getName()))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Attempted to cd into a non-existent directory"));
                    }
                }

                // ls commands are ignored; we'll assume everything not starting with `$` are directory contents
                //endregion
            } else {
                //region Directory contents
                if (line.startsWith("dir")) {
                    var directoryName = line.replace("dir ", "");
                    currentDirectory.addRecord(new Directory(currentDirectory, directoryName));
                } else {
                    var sizeAndName = line.split(" ");
                    currentDirectory.addRecord(new File(currentDirectory, sizeAndName[1], Integer.parseInt(sizeAndName[0])));
                }
                //endregion
            }
        }
    }

    private static int walkDirectoryTree(Directory currentDirectory) {
        var sumOfQualifiedDirectories = 0;

        if (currentDirectory.getSize() <= 100_000) {
            sumOfQualifiedDirectories += currentDirectory.getSize();
        }

        for (Directory subdirectory : currentDirectory.getSubdirectories()) {
            sumOfQualifiedDirectories += walkDirectoryTree(subdirectory);
        }

        return sumOfQualifiedDirectories;
    }
}
