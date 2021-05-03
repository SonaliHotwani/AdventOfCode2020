package advent.of.code.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Problem1 {
    public static void main(String[] args) throws IOException {
        List<Integer> adapterJoltages = Files.readAllLines(Paths.get("resources/Day10_AdapterJoltages")).stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        int oneJoltagedifference = 0;
        int threeJoltagedifference = 0;
        adapterJoltages.add(0, 0);
        adapterJoltages.add(adapterJoltages.size(), adapterJoltages.get(adapterJoltages.size() - 1) + 3);
        for (int i = 0; i < adapterJoltages.size() - 1; i++) {
            int difference = adapterJoltages.get(i + 1) - adapterJoltages.get(i);
            switch (difference) {
                case 1:
                    oneJoltagedifference++;
                    break;
                case 3:
                    threeJoltagedifference++;
                    break;
            }
        }
        System.out.println("1 joltage differences: " + oneJoltagedifference);
        System.out.println("3 joltage differences: " + threeJoltagedifference);
        System.out.println("Product: " + (oneJoltagedifference * threeJoltagedifference));
    }
}
