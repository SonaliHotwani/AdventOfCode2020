package advent.of.code.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Problem2 {
    public static void main(String[] args) throws IOException {
        final List<Long> numbers = Files.readAllLines(Paths.get("resources/Day9_XMAS_Numbers")).stream().map(Long::parseLong).collect(Collectors.toList());
        final int preamble = 25;
        Long invalidNumber = 0L;
        for (int i = preamble; i < numbers.size(); i++) {
            final List<Long> preambleOf5Numbers = numbers.subList(i - preamble, i);
            final boolean sumIsPresent = Problem1.findIfSumIsPresent(numbers, i, preambleOf5Numbers);
            if (!sumIsPresent) {
                invalidNumber = numbers.get(i);
                break;
            }
        }

        for (int i = 0; i < numbers.size(); i++) {
            Long sum = 0L;
            int j = i;
            while (sum < invalidNumber) {
                sum += numbers.get(j);
                j++;
            }
            if (sum.equals(invalidNumber)) {
                final List<Long> range = numbers.subList(i, j);
                final List<Long> sorted = range.stream().sorted().collect(Collectors.toList());
                final long finalSum = sorted.get(0) + sorted.get(sorted.size() - 1);
                System.out.println(finalSum);
                break;
            }
        }

    }
}
