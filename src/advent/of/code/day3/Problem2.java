package advent.of.code.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem2 {
    public static void main(String[] args) throws IOException {
        final List<String> allRows = Files.readAllLines(Paths.get("resources/Day3_Map.txt"));

        final long[] allTreesCount = {1};
        List<Slope> slopes = new ArrayList<Slope>() {{
            add(new Slope(1, 1));
            add(new Slope(3, 1));
            add(new Slope(5, 1));
            add(new Slope(7, 1));
            add(new Slope(1, 2));
        }};
        slopes.forEach((slope) -> {
            final AtomicInteger treesCount = new AtomicInteger();
            int initialPosition = 0;
            for (int i = 0; i < allRows.size() - slope.getDown(); i = i + slope.getDown()) {
                final int positionAfterRight = initialPosition + slope.getRight();
                if (positionAfterRight >= allRows.get(i).length()) {
                    for (int j = i; j < allRows.size(); j++) {
                        final String initialString = allRows.remove(j);
                        allRows.add(j, initialString.concat(initialString));
                    }
                }
                if (allRows.get(i + slope.getDown()).charAt(positionAfterRight) == '#') {
                    treesCount.incrementAndGet();
                }
                initialPosition = positionAfterRight;
            }
            System.out.println(treesCount.get());
            allTreesCount[0] = allTreesCount[0] * treesCount.get();
        });

        System.out.println("\n" + allTreesCount[0]);
    }
}

class Slope {
    private Integer right;
    private Integer down;

    Slope(Integer right, Integer down) {
        this.right = right;
        this.down = down;
    }

    Integer getRight() {
        return right;
    }

    Integer getDown() {
        return down;
    }
}
