package advent.of.code.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem1 {

    public static void main(String[] args) throws IOException {
        final String input = new String(Files.readAllBytes(Paths.get("resources/Day6_YesAnswers")));

        final Pattern singleGroupAnswerPattern = Pattern.compile("\\s*^\\s*$\\s*", Pattern.MULTILINE);
        final Pattern multispace = Pattern.compile("\\s+");

        final String answersByAllGroups = Stream.of(singleGroupAnswerPattern.split(input))
                .map(para -> multispace.matcher(para).replaceAll(""))
                .collect(Collectors.joining("\n"));

        final String[] answers = answersByAllGroups.split("\n");

        final Optional<Integer> allQuestionsAnsweredYes = Arrays.stream(answers).map(answer -> {
            final HashSet<Character> uniqueQuestionsAnsweredYes = new HashSet<>();
            final char[] questionsAnsweredYes = answer.toCharArray();
            for (char question : questionsAnsweredYes) {
                uniqueQuestionsAnsweredYes.add(question);
            }
            return uniqueQuestionsAnsweredYes.size();
        }).reduce(Integer::sum);
        System.out.println(allQuestionsAnsweredYes.get());
    }
}
