package advent.of.code.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Problem1 {
    public static void main(String[] args) throws IOException {
        final List<Long> numbers = Files.readAllLines(Paths.get("resources/Day9_XMAS_Numbers")).stream().map(Long::parseLong).collect(Collectors.toList());
        final int preamble = 25;
        for (int i = preamble; i < numbers.size(); i++) {
            final List<Long> preambleOf5Numbers = numbers.subList(i - preamble, i);
            final boolean sumIsPresent = findIfSumIsPresent(numbers, i, preambleOf5Numbers);
            if (!sumIsPresent) {
                System.out.println(numbers.get(i));
                break;
            }
        }
    }

    static boolean findIfSumIsPresent(List<Long> numbers, int i, List<Long> preambleOf5Numbers) {
        for (int j = 0; j < preambleOf5Numbers.size(); j++) {
            for (int k = 1; k < preambleOf5Numbers.size(); k++) {
                if ((preambleOf5Numbers.get(j) + preambleOf5Numbers.get(k)) == (numbers.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }
}
