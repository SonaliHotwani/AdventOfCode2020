package advent.of.code.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem2 {
    public static void main(String[] args) throws IOException {
        final List<String> passwordInputInString = Files.readAllLines(Paths.get("resources/Day2_PasswordInput.csv"));
        AtomicInteger validPasswords = new AtomicInteger();
        passwordInputInString.forEach(input -> {
            final String[] policyAndSample = input.split(" ");
            final int initialPosition = Integer.parseInt(policyAndSample[0].split("-")[0]);

            final int endPosition = Integer.parseInt(policyAndSample[0].split("-")[1]);

            final char character = policyAndSample[1].charAt(0);

            final String password = policyAndSample[2];

            final int firstIndex = initialPosition - 1;
            final int lastIndex = endPosition - 1;
            if ((password.charAt(firstIndex) == character && password.charAt(lastIndex) != character) ||
                    (password.charAt(lastIndex) == character && password.charAt(firstIndex) != character)) {
                validPasswords.incrementAndGet();
            }

        });
        System.out.println("Valid Passwords " + validPasswords.get());
    }
}
