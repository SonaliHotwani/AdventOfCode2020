package advent.of.code.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem1 {
    public static void main(String[] args) throws IOException {
        final List<String> allRows = Files.readAllLines(Paths.get("resources/Day3_Map.txt"));
        int initialPosition = 0;
        final AtomicInteger treesCount = new AtomicInteger();
        for (int i = 0; i < allRows.size() - 1; i++) {
            final int positionAfterRightShiftBy3 = initialPosition + 3;
            if (positionAfterRightShiftBy3 >= allRows.get(i).length()) {
                for (int j = i; j < allRows.size(); j++) {
                    final String initialString = allRows.remove(j);
                    allRows.add(j, initialString.concat(initialString));
                }
            }
            if (allRows.get(i + 1).charAt(positionAfterRightShiftBy3) == '#') {
                treesCount.incrementAndGet();
            }
            initialPosition = positionAfterRightShiftBy3;
        }
        System.out.println(treesCount.get());
    }
}
