package advent.of.code.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem1 {
    public static void main(String[] args) throws IOException {
        final String input = new String(Files.readAllBytes(Paths.get("resources/Day4_Passports.txt")));

        final Pattern singlePassportPattern = Pattern.compile("\\s*^\\s*$\\s*", Pattern.MULTILINE);
        final Pattern MULTISPACE = Pattern.compile("\\s+");

        final String passportInSingleLineString = Stream.of(singlePassportPattern.split(input))
                .map(para -> MULTISPACE.matcher(para).replaceAll(" "))
                .collect(Collectors.joining("\n"));

        final String[] passports = passportInSingleLineString.split("\n");

        final long validPassportsCount = Arrays.stream(passports).map(passportInLine -> {
            final String[] passportAttributes = passportInLine.split(" ");
            final Passport passport = new Passport();
            Arrays.stream(passportAttributes).forEach(passport::setAttribute);
            return passport;
        }).filter(Passport::isValid).count();
        System.out.println(validPassportsCount);
    }
}

class Passport {
    private String birthYear;
    private String issueYear;
    private String expirationYear;
    private String height;
    private String hairColor;
    private String eyeColor;
    private String passportId;
    private String countryId;

    private void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    private void setIssueYear(String issueYear) {
        this.issueYear = issueYear;
    }

    private void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    private void setHeight(String height) {
        this.height = height;
    }

    private void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    private void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    private void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    private void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    void setAttribute(String attributeString) {
        final Attribute attribute = getAttributeFrom(attributeString);
        final String attributeValue = attribute.getValue();
        switch (attribute.getName()) {
            case "byr":
                setBirthYear(attributeValue);
                break;
            case "ecl":
                setEyeColor(attributeValue);
                break;
            case "pid":
                setPassportId(attributeValue);
                break;
            case "eyr":
                setExpirationYear(attributeValue);
                break;
            case "iyr":
                setIssueYear(attributeValue);
                break;
            case "hcl":
                setHairColor(attributeValue);
                break;
            case "hgt":
                setHeight(attributeValue);
                break;
            case "cid":
                setCountryId(attributeValue);
                break;
        }
    }

    private Attribute getAttributeFrom(String attribute) {
        final String[] nameAndValue = attribute.split(":");
        return new Attribute(nameAndValue[0].trim(), nameAndValue[1].trim());
    }

    boolean isValid() {
        return Objects.nonNull(this.birthYear)
                && Objects.nonNull(this.issueYear)
                && Objects.nonNull(this.expirationYear)
                && Objects.nonNull(this.height)
                && Objects.nonNull(this.hairColor)
                && Objects.nonNull(this.eyeColor)
                && Objects.nonNull(this.passportId);
    }
}

class Attribute {
    private String name;
    private String value;

    Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    String getName() {
        return name;
    }

    String getValue() {
        return value;
    }
}
