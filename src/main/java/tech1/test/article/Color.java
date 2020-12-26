package tech1.test.article;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Color {
    RED,
    BLUE,
    GREEN;

    public static String getValuesAsString() {
        List<String> colors = Arrays.stream(Color.values())
                .map(Color::toString)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        return String.join(", ", colors);
    }
}
