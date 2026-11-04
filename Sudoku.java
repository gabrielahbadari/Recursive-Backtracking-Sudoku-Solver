/**
 * Sudoku.java
 *
 * Solves standard 9x9 Sudoku puzzles using recursive backtracking.
 *
 * Uses boolean lookup arrays to track values in rows, columns, and
 * 3x3 subgrids, allowing constant-time constraint checks during
 * the backtracking process.
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Sudoku {

    // Puzzle state and constraint tracking
    private final int[][] grid;
    private final boolean[][] valIsFixed;
    private final boolean[][][] subgridHasVal;
    private final boolean[][] rowHasVal;
    private final boolean[][] colHasVal;

    /**
     * Initializes an empty 9x9 Sudoku board and its constraint arrays.
     * Index 0 represents an empty cell, valid Sudoku values are 1 through 9.
     */
    public Sudoku() {
        grid = new int[9][9];
        valIsFixed = new boolean[9][9];
        subgridHasVal = new boolean[3][3][10];
        rowHasVal = new boolean[9][10];
        colHasVal = new boolean[9][10];
    }

    /**
     * Places a value on the board and updates its row, column, and subgrid.
     */
    private void placeVal(int val, int row, int col) {
        grid[row][col] = val;
        rowHasVal[row][val] = true;
        colHasVal[col][val] = true;
        subgridHasVal[row / 3][col / 3][val] = true;
    }

    /**
     * Removes a value and restores the constraint arrays during backtracking.
     */
    private void removeVal(int val, int row, int col) {
        grid[row][col] = 0;
        rowHasVal[row][val] = false;
        colHasVal[col][val] = false;
        subgridHasVal[row / 3][col / 3][val] = false;
    }

    /**
     * Reads a 9x9 puzzle from a file.
     * Nonzero values are treated as fixed clues that cannot be changed.
     */
    public void readConfig(Scanner input) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int val = input.nextInt();

                if (val != 0) {
                    placeVal(val, row, col);
                    valIsFixed[row][col] = true;
                }
            }
        }
    }

    /**
     * Recursively solves the puzzle, processing cells in row-major order.
     *
     * For each empty cell, tries values 1 through 9. A value is placed
     * only if it does not already appear in the same row, column, or
     * 3x3 subgrid. If a choice leads to a dead end, it is removed.
     *
     * @param n index of the current cell, from 0 to 81
     * @return true if a solution is found, false otherwise
     */
    private boolean solveRB(int n) {
        // All 81 cells have been processed successfully.
        if (n == 81) {
            return true;
        }

        int row = n / 9;
        int col = n % 9;

        // Skip cells containing fixed clues.
        if (valIsFixed[row][col]) {
            return solveRB(n + 1);
        }

        // Try each possible value for the current cell.
        for (int val = 1; val <= 9; val++) {
            boolean isValid = !rowHasVal[row][val]
                    && !colHasVal[col][val]
                    && !subgridHasVal[row / 3][col / 3][val];

            if (isValid) {
                placeVal(val, row, col);

                if (solveRB(n + 1)) {
                    return true;
                }

                // Undo the placement if the recursive attempt fails.
                removeVal(val, row, col);
            }
        }

        // No valid value leads to a solution.
        return false;
    }

    /**
     * Starts the recursive backtracking process.
     *
     * @return true if the puzzle is solved, false if no solution exists
     */
    public boolean solve() {
        return solveRB(0);
    }

    /**
     * Prints the current Sudoku board.
     * Empty cells are displayed as blank spaces.
     */
    public void printGrid() {
        for (int row = 0; row < 9; row++) {
            printRowSeparator();

            for (int col = 0; col < 9; col++) {
                System.out.print("|");

                if (grid[row][col] == 0) {
                    System.out.print("   ");
                } else {
                    System.out.print(" " + grid[row][col] + " ");
                }
            }

            System.out.println("|");
        }

        printRowSeparator();
    }

    /** Prints a horizontal separator between rows. */
    private static void printRowSeparator() {
        for (int i = 0; i < 9; i++) {
            System.out.print("----");
        }
        System.out.println("-");
    }

    /**
     * Loads a puzzle from a file, displays the initial board,
     * attempts to solve it, and prints the resulting board.
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Sudoku puzzle = new Sudoku();

        System.out.print("Enter the name of the puzzle file: ");
        String filename = console.nextLine();

        try (Scanner input = new Scanner(new File(filename))) {
            puzzle.readConfig(input);
        } catch (IOException e) {
            System.err.println("Error accessing file: " + filename);
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("\nInitial puzzle:");
        puzzle.printGrid();
        System.out.println();

        if (puzzle.solve()) {
            System.out.println("Solved puzzle:");
        } else {
            System.out.println("No solution could be found.");
            System.out.println("Current puzzle state:");
        }

        puzzle.printGrid();
    }
}