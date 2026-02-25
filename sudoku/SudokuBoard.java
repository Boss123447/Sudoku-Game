package sudoku;

public class SudokuBoard {
    private final int[][] grid;
    private final boolean[][] fixed;
    private final int[][] original; 

    public SudokuBoard() {
        this.grid = new int[9][9];
        this.fixed = new boolean[9][9];
        this.original = new int[9][9];
    }

    // Allows for a puzzle to be loaded
    public SudokuBoard(int[][] puzzle) {
        if (puzzle == null || puzzle.length != 9) {
            throw new IllegalArgumentException("Puzzle must be 9x9");
        }

        this.grid = new int[9][9];
        this.fixed = new boolean[9][9];
        this.original = new int[9][9];

        for (int r = 0; r < 9; r++) {
            if (puzzle[r] == null || puzzle[r].length != 9) {
                throw new IllegalArgumentException("Puzzle must be 9x9");
            }
            for (int c = 0; c < 9; c++) {
                int v = puzzle[r][c];
                if (v < 0 || v > 9) {
                    throw new IllegalArgumentException("Values nust be 0-9");
                }
                grid[r][c] = v;
                original[r][c] = v;
                fixed[r][c] = (v != 0);
            }
        }
    }

    public int get(int row, int col) {
        return grid[row][col];
    }

    public boolean isFixed(int row, int col) {
        return fixed[row][col];
    }

    public void forceSet(int row, int col, int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Value must be 0-9");
        }
        grid[row][col] = value;
    }

    public boolean trySet(int row, int col, int value) {
        if (value < 0 || value > 9) {
            return false;
        }

        if (fixed[row][col]) {
            return false;
        }

        if (value == 0) {
            grid[row][col] = 0;
            return true;
        }

        if (!isLegalMove(row, col, value)) {
            return false;
        }

        grid[row][col] = value;
        return true;
    }

    public boolean isLegalMove(int row, int col, int value) {
        if (value < 1 || value > 9) {
            return false;
        }

        int curr = grid[row][col];
        grid[row][col] = 0;

        // Row Check
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == value) {
                grid[row][col] = curr;
                return false;
            }
        }

        // Column Check
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == value) {
                grid[row][col] = curr;
                return false;
            }
        }

        // Subgrid Check
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (grid[i][j] == value) {
                    grid[row][col] = curr;
                    return false;
                }
            }
        }

        grid[row][col] = curr;
        return true;
    }

    public void reset() {
        for (int r = 0; r < 9; r++) {
            System.arraycopy(original[r], 0, grid[r], 0, 9);
        }
    }

   public boolean isSolved() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = grid[r][c];
                if (v == 0) return false;
                if (!isLegalMove(r, c, v)) return false;
            }
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < 9; i++) {
            if ( i % 3 == 0) {
                System.out.println("+-------+-------+-------+");
            }
            for (int j = 0; j < 9; j++){
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(grid[i][j] == 0 ? ". " : grid[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }

    public int[][] toArray() {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            System.arraycopy(grid[r], 0, copy[r], 0, 9);
        }
        return copy;
    }
}
