package advent.of.code.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Problem1 {
    public static void main(String[] args) throws IOException {
        AtomicInteger result = new AtomicInteger(0);
        final List<String> instructions = Files.readAllLines(Paths.get("resources/Day8_Instructions"));
        List<Boolean> instructionsVisited = instructions.stream().map(s -> false).collect(Collectors.toList());

        for (int i = 0; i < instructions.size(); ) {
            final String instruction = instructions.get(i);
            final Boolean visited = instructionsVisited.get(i);
            final String instructionName = instruction.substring(0, 4).trim();
            final int instructionValue = Integer.parseInt(instruction.replaceAll("[^-0-9]", ""));
            if (!visited) {
                instructionsVisited.remove(i);
                instructionsVisited.add(i, true);

                switch (instructionName) {
                    case "acc":
                        result.addAndGet(instructionValue);
                        i++;
                        break;
                    case "jmp":
                        i = i + instructionValue;
                        break;
                    case "nop":
                        i++;
                        break;
                }
            } else break;
        }
        System.out.println(result.get());
    }
}
