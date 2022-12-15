package ch.mikeko.adventofcode2022.common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public class InputParser {
    public static Stream<String> getInputByLine(int day, InputType inputType) {
        var path = String.format("/ch/mikeko/adventofcode2022/%s/" + inputType.getFileName(), day);

        try {
            return Files.lines(Path.of(Objects.requireNonNull(InputParser.class.getResource(path)).toURI()), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
