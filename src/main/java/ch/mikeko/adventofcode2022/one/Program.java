package ch.mikeko.adventofcode2022.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var list = new ArrayList<Integer>();

        URL resource = Program.class.getResource("/ch/mikeko/adventofcode2022/one/input.txt");
        try (Stream<String> lines = Files.lines(Path.of(Objects.requireNonNull(resource).toURI()), Charset.defaultCharset())) {
            var collectedLines = lines.collect(Collectors.toList());

            var runningTotal = 0;
            for (var line : collectedLines) {
                if ("".equals(line)) {
                    list.add(runningTotal);
                    runningTotal = 0;
                    continue;
                }

                runningTotal += Integer.parseInt(line);
            }
        }

        list.sort((o1, o2) -> o2 - o1);

        System.out.printf("Part one solution: %s%n", list.get(0));

        var topThreeTotal = list.get(0) + list.get(1) + list.get(2);
        System.out.printf("Part two solution: %s%n", topThreeTotal);
    }
}
