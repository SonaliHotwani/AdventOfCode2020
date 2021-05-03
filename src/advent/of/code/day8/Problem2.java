package advent.of.code.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Problem2 {
    public static void main(String[] args) throws IOException {

        List<String> instructions = Files.readAllLines(Paths.get("resources/Day8_Instructions"));
        List<Boolean> instructionsVisited;
        List<String> mayBeBugs = instructions.stream()
                .filter(inst -> inst.substring(0, 4).trim().equals("jmp") || inst.substring(0, 4).trim().equals("nop"))
                .collect(Collectors.toList());

        for (String bug : mayBeBugs) {
            instructions = Files.readAllLines(Paths.get("resources/Day8_Instructions"));
            AtomicInteger result = new AtomicInteger(0);
            int ip = 0;
            instructionsVisited = instructions.stream().map(s -> false).collect(Collectors.toList());
            while (ip < instructions.size() && !instructionsVisited.get(ip)) {
                instructionsVisited.remove(ip);
                instructionsVisited.add(ip, true);
                String instruction = instructions.get(ip);
                String instructionName = instruction.substring(0, 4).trim();
                final int instructionValue = Integer.parseInt(instruction.replaceAll("[^-0-9]", ""));
                if (instructions.get(ip).equals(bug)) {
                    if (instructionName.equals("jmp")) {
                        instructions.remove(ip);
                        final String newInstruction = String.format("nop %d", instructionValue);
                        instructionName = newInstruction.substring(0, 4).trim();
                        instructions.add(ip, newInstruction);
                    } else {
                        instructions.remove(ip);
                        final String newInstruction = String.format("jmp %d", instructionValue);
                        instructionName = newInstruction.substring(0, 4).trim();
                        instructions.add(ip, newInstruction);
                    }
                }
                if (instructionName.equals("acc")) {
                    result.addAndGet(instructionValue);
                    ip++;
                } else if (instructionName.equals("jmp")) {
                    ip = ip + instructionValue;
                } else {
                    ip++;
                }
            }
            if (ip == instructions.size()) {
                System.out.println("Result2: " + result.get());
                break;
            }
        }
    }
}
