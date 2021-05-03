package advent.of.code.day5;

import kotlin.ranges.IntRange;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Problem2 {
    public static void main(String[] args) throws IOException {
        final List<String> boardingPassNumbers = Files.readAllLines(Paths.get("resources/Day5_BoardingPassNumbers"));

        final List<Integer> seatIds = boardingPassNumbers
                .stream()
                .map(pass -> {
                    Integer row = findRow(firstSevenCharactersForRow(pass));
                    Integer column = findColumn(lastThreeCharactersForColumn(pass));
                    return new AircraftSeat(row, column).seatId();
                }).collect(Collectors.toList());

        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 8; j++) {
                final int currentSeatId = (i * 8) + j;
                if (!seatIds.contains(new AircraftSeat(i, j).seatId()) && seatIds.contains(currentSeatId + 1) && seatIds.contains(currentSeatId - 1)) {
                    System.out.println("Your Row: " + i + " Your Column: " + j);
                }
            }
        }
    }

    private static Integer findRow(String rowCharacters) {
        return findNumber(rowCharacters, new IntRange(0, 127), 'F', 'B');
    }

    private static Integer findColumn(String columnCharacters) {
        return findNumber(columnCharacters, new IntRange(0, 7), 'L', 'R');
    }

    private static Integer findNumber(String columnCharacters, IntRange initialRange, char lowerHalfCharacter, char upperHalfCharacter) {
        final char[] chars = columnCharacters.toCharArray();
        for (char aChar : chars) {
            final Integer start = initialRange.getStart();
            final Integer end = initialRange.getEndInclusive();
            final int halfRange = (end - start) / 2;
            if (aChar == lowerHalfCharacter) {
                initialRange = new IntRange(start, end - halfRange - 1);
            } else if (aChar == upperHalfCharacter) {
                initialRange = new IntRange(start + halfRange + 1, end);
            }
        }
        if (initialRange.getStart().equals(initialRange.getEndInclusive()))
            return initialRange.getStart();
        return -1;
    }

    private static String firstSevenCharactersForRow(String pass) {
        return pass.substring(0, 7);
    }

    private static String lastThreeCharactersForColumn(String pass) {
        return pass.substring(7, 10);
    }
}

class AircraftSeat {
    private int row;
    private int column;

    AircraftSeat(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    Integer seatId() {
        return ((this.row * 8) + this.column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AircraftSeat that = (AircraftSeat) o;
        return row == that.row &&
                column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
