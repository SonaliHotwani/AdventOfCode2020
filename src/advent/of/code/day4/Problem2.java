package advent.of.code.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Problem2 {
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
            final Passport2 passport = new Passport2();
            Arrays.stream(passportAttributes).forEach(passport::setAttribute);
            return passport;
        }).filter(Passport2::isValid).count();
        System.out.println(validPassportsCount);
    }
}

class Passport2 {
    private Attribute2 birthYear;
    private Attribute2 issueYear;
    private Attribute2 expirationYear;
    private Attribute2 height;
    private Attribute2 hairColor;
    private Attribute2 eyeColor;
    private Attribute2 passportId;
    private Attribute2 countryId;


    void setAttribute(String attributeString) {
        final Attribute2 attribute = getAttributeFrom(attributeString);
        switch (attribute.getName()) {
            case "byr":
                setBirthYear(attribute);
                break;
            case "ecl":
                setEyeColor(attribute);
                break;
            case "pid":
                setPassportId(attribute);
                break;
            case "eyr":
                setExpirationYear(attribute);
                break;
            case "iyr":
                setIssueYear(attribute);
                break;
            case "hcl":
                setHairColor(attribute);
                break;
            case "hgt":
                setHeight(attribute);
                break;
            case "cid":
                setCountryId(attribute);
                break;
        }
    }

    private Attribute2 getAttributeFrom(String attribute) {
        final String[] nameAndValue = attribute.split(":");
        return new Attribute2(nameAndValue[0].trim(), nameAndValue[1].trim());
    }

    boolean isValid() {
        return Objects.nonNull(this.birthYear)
                && Objects.nonNull(this.issueYear)
                && Objects.nonNull(this.expirationYear)
                && Objects.nonNull(this.height)
                && Objects.nonNull(this.hairColor)
                && Objects.nonNull(this.eyeColor)
                && Objects.nonNull(this.passportId)
                && this.birthYear.isValid("^(19[2-9][0-9])|(200[0-2])$")
                && this.issueYear.isValid("^(201[0-9])|(2020)$")
                && this.expirationYear.isValid("^(202[0-9])|(2030)$")
                && this.height.isValid("^(1[5-8][0-9])cm|(19[0-3])cm|(59)in|(6[0-9])in|(7[0-6])in$")
                && this.hairColor.isValid("^#[a-f0-9]{6}$")
                && this.eyeColor.isValid("^[amb|blu|brn|gry|grn|hzl|oth]{3}$")
                && this.passportId.isValid("^[0-9]{9}$");
    }

    private void setBirthYear(Attribute2 birthYear) {
        this.birthYear = birthYear;
    }

    private void setIssueYear(Attribute2 issueYear) {
        this.issueYear = issueYear;
    }

    private void setExpirationYear(Attribute2 expirationYear) {
        this.expirationYear = expirationYear;
    }

    private void setHeight(Attribute2 height) {
        this.height = height;
    }

    private void setHairColor(Attribute2 hairColor) {
        this.hairColor = hairColor;
    }

    private void setEyeColor(Attribute2 eyeColor) {
        this.eyeColor = eyeColor;
    }

    private void setPassportId(Attribute2 passportId) {
        this.passportId = passportId;
    }

    private void setCountryId(Attribute2 countryId) {
        this.countryId = countryId;
    }
}

class Attribute2 {
    private String name;
    private String value;

    Attribute2(String name, String value) {
        this.name = name;
        this.value = value;
    }

    String getName() {
        return name;
    }

    boolean isValid(String regex) {
        return Objects.nonNull(value) && value.matches(regex);
    }
}
