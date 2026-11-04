# Sudoku Solver

A Java-based Sudoku solver that uses **recursive backtracking** and optimized constraint checking to solve standard 9×9 Sudoku puzzles. The program uses boolean lookup arrays to track values across rows, columns, and 3×3 subgrids, allowing efficient validation of possible number placements.

## Features

- **Recursive Backtracking** - Explores valid number placements and backtracks when a path leads to a dead end.
- **Optimized Constraint Checking** - Uses boolean lookup arrays to efficiently check row, column, and subgrid constraints.
- **Fixed Cell Preservation** - Keeps the original puzzle clues unchanged throughout the solving process.
- **File-Based Input** - Reads Sudoku puzzles from text files.
- **Console Visualization** - Displays the initial puzzle and resulting board in a formatted grid.
- **Unsolvable Puzzle Handling** - Reports when no valid solution can be found.

## How It Works

The solver processes the Sudoku grid from left to right, top to bottom, using recursive backtracking.

1. **Identify the current cell.** If the cell contains a fixed value, skip it and move to the next position.
2. **Check possible values.** For an empty cell, try numbers from 1 through 9, checking whether each satisfies the row, column, and 3×3 subgrid constraints.
3. **Place a valid number.** Update the board and the corresponding boolean lookup arrays.
4. **Continue recursively.** Move to the next cell and repeat the process.
5. **Backtrack when necessary.** If no valid number can be placed, undo the previous placement and try another possibility.
6. **Return the result.** The algorithm succeeds when all 81 cells have been processed or reports failure when no solution is found.

### Constraint Checking

Instead of repeatedly scanning rows, columns, and subgrids, the solver maintains three lookup structures.

| Data Structure | Purpose |
| --- | --- |
| `rowHasVal[9][10]` | Tracks which numbers appear in each row |
| `colHasVal[9][10]` | Tracks which numbers appear in each column |
| `subgridHasVal[3][3][10]` | Tracks which numbers appear in each 3×3 subgrid |

These arrays allow the solver to check whether a number can be placed in a cell without scanning the surrounding cells.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- A terminal or Java-compatible IDE

### Running the Program

**1. Clone the repository**

```bash
git clone https://github.com/gabrielahbadari/Sudoku-Solver.git
cd Sudoku-Solver
```

**2. Compile the Java program**

```bash
javac Sudoku.java
```

**3. Run the solver**

```bash
java Sudoku
```

**4. Enter the path to a Sudoku puzzle file**

When prompted, enter the filename of a Sudoku puzzle.

```text
Enter the name of the puzzle file: puzzle.txt
```

The input file must contain nine rows of nine space-separated integers, with `0` representing an empty cell.

Example input

```text
5 3 0 0 7 0 0 0 0
6 0 0 1 9 5 0 0 0
0 9 8 0 0 0 0 6 0
8 0 0 0 6 0 0 0 3
4 0 0 8 0 3 0 0 1
7 0 0 0 2 0 0 0 6
0 6 0 0 0 0 2 8 0
0 0 0 4 1 9 0 0 5
0 0 0 0 8 0 0 7 9
```

The program displays the initial puzzle, attempts to solve it, and prints the completed board if a solution is found.

## Technologies

- Java
- Recursive Backtracking
- Multidimensional Arrays
- Constraint Checking
- File I/O
