package advent.of.code.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Problem1 {
    public static void main(String[] args) throws IOException {
        final List<char[]> seatArrangement = Files.readAllLines(Paths.get("resources/Day11_SeatingArrangement"))
                .stream()
                .map(String::toCharArray)
                .collect(Collectors.toList());
        List<char[]> previousSeatArrangement = new ArrayList<>(seatArrangement);
        List<char[]> currentSeatArrangement = new ArrayList<>();
        while (!currentEqualsPrevious(previousSeatArrangement, currentSeatArrangement)) {

            previousSeatArrangement = seatArrangement.stream().map(char[]::clone).collect(Collectors.toList());
            for (int i = 0; i < previousSeatArrangement.size(); i++) {
                final char[] previousSeatsInSingleLine = previousSeatArrangement.get(i);
                final char[] seatsInSingleLine = seatArrangement.get(i);
                for (int j = 0; j < previousSeatsInSingleLine.length; j++) {
                    if (previousSeatsInSingleLine[j] == 'L' && occupiedAdjacentSeats(previousSeatArrangement, i, j) == 0) {
                        seatsInSingleLine[j] = '#';
                    } else if (previousSeatsInSingleLine[j] == '#' && occupiedAdjacentSeats(previousSeatArrangement, i, j) >= 4) {
                        seatsInSingleLine[j] = 'L';
                    }
                }
            }
            currentSeatArrangement = seatArrangement.stream().map(char[]::clone).collect(Collectors.toList());
        }

        int totalOccupiedSeats = 0;
        for (char[] chars : currentSeatArrangement) {
            for (char aChar : chars) {
                if (aChar == '#') {
                    totalOccupiedSeats++;
                }
            }
        }
        System.out.println(totalOccupiedSeats);
    }

    static boolean currentEqualsPrevious(List<char[]> previousSeatArrangement, List<char[]> currentSeatArrangement) {
        if (previousSeatArrangement.size() == 0 | currentSeatArrangement.size() == 0) {
            return previousSeatArrangement.equals(currentSeatArrangement);
        }
        for (int i = 0; i < previousSeatArrangement.size(); i++) {
            for (int j = 0; j < previousSeatArrangement.get(i).length; j++) {
                if (previousSeatArrangement.get(i)[j] != currentSeatArrangement.get(i)[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    static void printArrangement(List<char[]> seatArrangement) {
        seatArrangement.forEach(System.out::println);
    }

    private static Integer occupiedAdjacentSeats(List<char[]> seatArrangement, int row, int column) {
        final int maxColumnLength = seatArrangement.get(row).length;
        final int maxRowLength = seatArrangement.size();
        final char upSeat;
        final char rightDiagonalUpSeat;
        final char rightDiagonalDownSeat;
        final char rightSeat;
        final char leftSeat;
        final char downSeat;
        final char leftDiagonalDownSeat;
        final char leftDiagonalUpSeat;
        if (row == 0 && column == 0) {                                              //First Row, First Column
            downSeat = seatArrangement.get(row + 1)[column];
            rightSeat = seatArrangement.get(row)[column + 1];
            rightDiagonalDownSeat = seatArrangement.get(row + 1)[column + 1];
            return findOccupied(Arrays.asList(downSeat, rightSeat, rightDiagonalDownSeat));
        } else if (row == 0 && column > 0 && column < maxColumnLength - 1) {                      //First Row, Till Last-1 Column
            downSeat = seatArrangement.get(row + 1)[column];
            rightSeat = seatArrangement.get(row)[column + 1];
            leftSeat = seatArrangement.get(row)[column - 1];
            rightDiagonalDownSeat = seatArrangement.get(row + 1)[column + 1];
            leftDiagonalDownSeat = seatArrangement.get(row + 1)[column - 1];
            return findOccupied(Arrays.asList(downSeat, rightSeat, rightDiagonalDownSeat, leftSeat, leftDiagonalDownSeat));
        } else if (row == 0 && column == maxColumnLength - 1) {                                 //First Row, Last Column
            downSeat = seatArrangement.get(row + 1)[column];
            leftSeat = seatArrangement.get(row)[column - 1];
            leftDiagonalDownSeat = seatArrangement.get(row + 1)[column - 1];
            return findOccupied(Arrays.asList(downSeat, leftSeat, leftDiagonalDownSeat));
        } else if (row > 0 && row < maxRowLength - 1 && column == 0) {                         //Second Row Till Last-1 Row, First Column
            upSeat = seatArrangement.get(row - 1)[column];
            downSeat = seatArrangement.get(row + 1)[column];
            rightDiagonalUpSeat = seatArrangement.get(row - 1)[column + 1];
            rightDiagonalDownSeat = seatArrangement.get(row + 1)[column + 1];
            rightSeat = seatArrangement.get(row)[column + 1];
            return findOccupied(Arrays.asList(upSeat, downSeat, rightDiagonalUpSeat, rightDiagonalDownSeat, rightSeat));
        } else if (row > 0 && row < maxRowLength - 1 && column == maxColumnLength - 1) {       //Second Row Till Last-1 Row, Last Column
            leftSeat = seatArrangement.get(row)[column - 1];
            upSeat = seatArrangement.get(row - 1)[column];
            downSeat = seatArrangement.get(row + 1)[column];
            leftDiagonalUpSeat = seatArrangement.get(row - 1)[column - 1];
            leftDiagonalDownSeat = seatArrangement.get(row + 1)[column - 1];
            return findOccupied(Arrays.asList(leftSeat, upSeat, leftDiagonalUpSeat, downSeat, leftDiagonalDownSeat));
        } else if (row == maxRowLength - 1 && column == 0) {                                  //Last Row, First Column
            upSeat = seatArrangement.get(row - 1)[column];
            rightSeat = seatArrangement.get(row)[column + 1];
            rightDiagonalUpSeat = seatArrangement.get(row - 1)[column + 1];
            return findOccupied(Arrays.asList(upSeat, rightSeat, rightDiagonalUpSeat));
        } else if (row == maxRowLength - 1 && column > 0 && column < maxColumnLength - 1) {   //Last Row, Second Column Till Last-1 Column                                     //Last Row
            leftSeat = seatArrangement.get(row)[column - 1];
            rightSeat = seatArrangement.get(row)[column + 1];
            upSeat = seatArrangement.get(row - 1)[column];
            leftDiagonalUpSeat = seatArrangement.get(row - 1)[column - 1];
            rightDiagonalUpSeat = seatArrangement.get(row - 1)[column + 1];
            return findOccupied(Arrays.asList(leftSeat, upSeat, leftDiagonalUpSeat, rightSeat, rightDiagonalUpSeat));
        } else if (row == maxRowLength - 1 && column == maxColumnLength - 1) {      //Last Row, Last Column
            leftSeat = seatArrangement.get(row)[column - 1];
            upSeat = seatArrangement.get(row - 1)[column];
            leftDiagonalUpSeat = seatArrangement.get(row - 1)[column - 1];
            return findOccupied(Arrays.asList(leftSeat, upSeat, leftDiagonalUpSeat));
        }
        upSeat = seatArrangement.get(row - 1)[column];
        rightDiagonalUpSeat = seatArrangement.get(row - 1)[column + 1];
        rightDiagonalDownSeat = seatArrangement.get(row + 1)[column + 1];
        rightSeat = seatArrangement.get(row)[column + 1];
        leftSeat = seatArrangement.get(row)[column - 1];
        downSeat = seatArrangement.get(row + 1)[column];
        leftDiagonalDownSeat = seatArrangement.get(row + 1)[column - 1];
        leftDiagonalUpSeat = seatArrangement.get(row - 1)[column - 1];
        return findOccupied(Arrays.asList(upSeat, rightDiagonalUpSeat, rightDiagonalDownSeat, rightSeat, leftSeat, downSeat, leftDiagonalDownSeat, leftDiagonalUpSeat));
    }

    static Integer findOccupied(List<Character> asList) {
        int occupiedSeats = 0;
        for (char c : asList) {
            if (c == '#')
                occupiedSeats++;
        }
        return occupiedSeats;
    }
}
