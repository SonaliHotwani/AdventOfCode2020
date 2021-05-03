package advent.of.code.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static advent.of.code.day11.Problem1.*;

public class Problem2 {
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
                    if (previousSeatsInSingleLine[j] == 'L' && visibleOccupiedSeats(previousSeatArrangement, i, j) == 0) {
                        seatsInSingleLine[j] = '#';
                    } else if (previousSeatsInSingleLine[j] == '#' && visibleOccupiedSeats(previousSeatArrangement, i, j) >= 5) {
                        seatsInSingleLine[j] = 'L';
                    }
                }
            }
            currentSeatArrangement = seatArrangement.stream().map(char[]::clone).collect(Collectors.toList());

            System.out.println("*****Previous******");
            printArrangement(previousSeatArrangement);
            System.out.println();
            System.out.println("*****Current******");
            printArrangement(currentSeatArrangement);
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

    private static Integer visibleOccupiedSeats(List<char[]> seatArrangement, int row, int column) {
        final int maxColumnLength = seatArrangement.get(row).length;
        final int maxRowLength = seatArrangement.size();
        List<Character> visibleSeats = new ArrayList<>();
        if (row == 0 && column == 0) {                                              //FIRST ROW, FIRST COLUMN
            for (int i = 1; column + i < maxColumnLength; i++) {
                visibleSeats.add(seatArrangement.get(row)[column + i]);             //right
            }
            for (int i = 1; row + i < maxRowLength; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column]);             //down
            }
            for (int i = 1; row + i < maxRowLength && column + i < maxColumnLength; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column + i]);         //rightDiagonalDown
            }
            return findOccupied(visibleSeats);
        } else if (row == 0 && column > 0 && column < maxColumnLength - 1) {        //FIRST ROW, TILL LAST-1 COLUMN
            for (int i = 0; i < maxColumnLength; i++) {
                if (i != column)
                    visibleSeats.add(seatArrangement.get(row)[i]);                  //right, left
            }
            for (int i = 1; row + i < maxRowLength; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column]);             //down
            }
            for (int i = 1; row + i < maxRowLength && column + i < maxColumnLength; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column + i]);         //rightDiagonalDown
            }
            for (int i = 1; row + i < maxRowLength && column - i >= 0; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column - i]);         //leftDiagonalDownSeat
            }
            return findOccupied(visibleSeats);
        } else if (row == 0 && column == maxColumnLength - 1) {                                 //First Row, Last Column
            for (int i = 1; row + i < maxRowLength; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column]);             //down
            }
            for (int i = 1; column - i >= 0; i++) {
                visibleSeats.add(seatArrangement.get(row)[column - i]);             //left
            }
            for (int i = 1; row + i < maxRowLength && column - i >= 0; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column - i]);         //leftDiagonalDownSeat
            }
            return findOccupied(visibleSeats);
        } else if (row > 0 && row < maxRowLength - 1 && column == 0) {                         //Second Row Till Last-1 Row, First Column
            for (int i = 0; i < maxRowLength; i++) {
                if (i != row)
                    visibleSeats.add(seatArrangement.get(i)[column]);              //up, down
            }
            for (int i = 0; i < maxColumnLength; i++) {
                if (i != column)
                    visibleSeats.add(seatArrangement.get(row)[i]);                  //right
            }
            for (int i = 1; row + i < maxRowLength && column + i < maxColumnLength; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column + i]);         //rightDiagonalDown
            }
            for (int i = 1; row - i >= 0 && column + i < maxColumnLength; i++) { //rightDiagonalUp
                visibleSeats.add(seatArrangement.get(row - i)[column + i]);
            }
            return findOccupied(visibleSeats);
        } else if (row > 0 && row < maxRowLength - 1 && column == maxColumnLength - 1) {       //Second Row Till Last-1 Row, Last Column
            for (int i = 0; i < maxColumnLength; i++) {
                if (i != column)
                    visibleSeats.add(seatArrangement.get(row)[i]);                  // left
            }
            for (int i = 0; i < maxRowLength; i++) {
                if (i != row)
                    visibleSeats.add(seatArrangement.get(i)[column]);              //up, down
            }
            for (int i = 1; row + i < maxRowLength && column - i >= 0; i++) {
                visibleSeats.add(seatArrangement.get(row + i)[column - i]);         //leftDiagonalDownSeat
            }
            for (int i = 1; row - i >= 0 && column - i >= 0; i++) {                 //leftDiagonalUpSeat
                visibleSeats.add(seatArrangement.get(row - i)[column - i]);
            }
            return findOccupied(visibleSeats);
        } else if (row == maxRowLength - 1 && column == 0) {                                  //Last Row, First Column
            for (int i = 1; row - i >= 0; i++) {
                visibleSeats.add(seatArrangement.get(row - i)[column]);             //up
            }
            for (int i = 0; i < maxColumnLength; i++) {
                if (i != column)
                    visibleSeats.add(seatArrangement.get(row)[i]);                  //right
            }
            for (int i = 1; row - i >= 0 && column + i < maxColumnLength; i++) { //rightDiagonalUp
                visibleSeats.add(seatArrangement.get(row - i)[column + i]);
            }
            return findOccupied(visibleSeats);
        } else if (row == maxRowLength - 1 && column > 0 && column < maxColumnLength - 1) {   //Last Row, Second Column Till Last-1 Column                                     //Last Row
            for (int i = 0; i < maxColumnLength; i++) {
                if (i != column)
                    visibleSeats.add(seatArrangement.get(row)[i]);                  //right, left
            }
            for (int i = 1; row - i >= 0; i++) {
                visibleSeats.add(seatArrangement.get(row - i)[column]);             //up
            }
            for (int i = 1; row - i >= 0 && column + i < maxColumnLength; i++) { //rightDiagonalUp
                visibleSeats.add(seatArrangement.get(row - i)[column + i]);
            }
            for (int i = 1; row - i >= 0 && column - i >= 0; i++) {                 //leftDiagonalUpSeat
                visibleSeats.add(seatArrangement.get(row - i)[column - i]);
            }
            return findOccupied(visibleSeats);
        } else if (row == maxRowLength - 1 && column == maxColumnLength - 1) {      //Last Row, Last Column
            for (int i = 0; i < maxColumnLength; i++) {
                if (i != column)
                    visibleSeats.add(seatArrangement.get(row)[i]);                  // left
            }
            for (int i = 1; row - i >= 0; i++) {
                visibleSeats.add(seatArrangement.get(row - i)[column]);             //up
            }
            for (int i = 1; row - i >= 0 && column - i >= 0; i++) {                 //leftDiagonalUpSeat
                visibleSeats.add(seatArrangement.get(row - i)[column - i]);
            }
            return findOccupied(visibleSeats);
        }
//        ***********************************************
        for (int i = 0; i < maxRowLength; i++) {
            if (i != row)
                visibleSeats.add(seatArrangement.get(i)[column]);              //up, down
        }
        for (int i = 0; i < maxColumnLength; i++) {
            if (i != column)
                visibleSeats.add(seatArrangement.get(row)[i]);                  //right, left
        }
        for (int i = 1; row - i >= 0 && column + i < maxColumnLength; i++) { //rightDiagonalUp
            visibleSeats.add(seatArrangement.get(row - i)[column + i]);
        }
        for (int i = 1; row + i < maxRowLength && column - i >= 0; i++) {
            visibleSeats.add(seatArrangement.get(row + i)[column - i]);         //leftDiagonalDownSeat
        }
        for (int i = 1; row - i >= 0 && column - i >= 0; i++) {                 //leftDiagonalUpSeat
            visibleSeats.add(seatArrangement.get(row - i)[column - i]);
        }
        for (int i = 1; row + i < maxRowLength && column + i < maxColumnLength; i++) {
            visibleSeats.add(seatArrangement.get(row + i)[column + i]);         //rightDiagonalDown
        }
        return findOccupied(visibleSeats);
    }
}
