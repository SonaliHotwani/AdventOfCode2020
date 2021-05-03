package advent.of.code.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem1 {
    public static void main(String[] args) throws IOException {
        final List<String> passwordInputInString = Files.readAllLines(Paths.get("resources/Day2_PasswordInput.csv"));
        AtomicInteger validPasswords = new AtomicInteger();
        passwordInputInString.forEach(input -> {
            final String[] policyAndSample = input.split(" ");
            final Integer min = Integer.parseInt(policyAndSample[0].split("-")[0]);
            System.out.println(min);
            final Integer max = Integer.parseInt(policyAndSample[0].split("-")[1]);
            System.out.println(max);
            final char character = policyAndSample[1].charAt(0);
            System.out.println(character);
            final String password = policyAndSample[2];
            System.out.println(password);

            final long count = password.chars().mapToObj(i -> (char) i).filter(char1 -> char1.equals(character)).count();
            if (count >= min && count <= max) {
                validPasswords.incrementAndGet();
            }
        });
        System.out.println("Valid Passwords " + validPasswords.get());
    }
}
