package advent.of.code.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Problem2 {
    public static void main(String[] args) throws IOException {
        List<Integer> adapterJoltages = Files.readAllLines(Paths.get("resources/Day10_AdapterJoltages")).stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        final List<Integer> sortedJoltages = adapterJoltages.stream().sorted().collect(Collectors.toList());
        sortedJoltages.add(0, 0);
        sortedJoltages.add(sortedJoltages.size(), sortedJoltages.get(sortedJoltages.size() - 1) + 3);
        Map<Integer, Long> alreadyProcessed = new HashMap<>();
        final Long combinations = getCombinations(sortedJoltages, 0, alreadyProcessed);
        System.out.println(combinations);
    }

    private static Long getCombinations(List<Integer> sortedJoltages, int position, Map<Integer, Long> alreadyProcessed) {
        if (position == sortedJoltages.size() - 1) {
            return 1L;
        }
        if (alreadyProcessed.get(position) != null) {
            return alreadyProcessed.get(position);
        }
        Long answer = 0L;
        for (int j = position + 1; j < sortedJoltages.size(); j++) {
            if (sortedJoltages.get(j) - sortedJoltages.get(position) <= 3) {
                answer += getCombinations(sortedJoltages, j, alreadyProcessed);
            }
        }
        alreadyProcessed.put(position, answer);
        return answer;
    }
}
