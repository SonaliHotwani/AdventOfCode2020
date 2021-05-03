package advent.of.code.day6;

import kotlin.ranges.CharRange;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class Problem2 {

    public static void main(String[] args) throws IOException {
        final String input = new String(Files.readAllBytes(Paths.get("resources/Day6_YesAnswers")));

        final String[] groupAnswers = input.split("\\n\\n");

        final Optional<Long> allCommonQuestionsAnsweredYes = Arrays.stream(groupAnswers).map(groupAnswer -> {
            final String[] individualAnswers = groupAnswer.split("\n");
            final HashMap<Character, Integer> characterCountMap = new HashMap<>();
            for (char c : new CharRange('a', 'z')) {
                characterCountMap.put(c, 0);
            }
            for (String answer : individualAnswers) {
                answer.chars()
                        .mapToObj(c -> (char) c)
                        .forEach(c -> characterCountMap.put(c, characterCountMap.get(c) + 1));
            }
            return characterCountMap.entrySet().stream().filter(entry -> entry.getValue() == individualAnswers.length).count();
        }).reduce(Long::sum);

        System.out.println(allCommonQuestionsAnsweredYes.get());
    }
}
