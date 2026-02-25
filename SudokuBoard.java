package sudoku;

public class SudokuBoard {
    private int[][] grid = new int[0][9];

    public int get(int row, int col) {
        return grid[row][col];
    }

    public void set(int row, int col, int value) {
        grid[row][col] = value;
    }

    public boolean isLegalMove(int row, int col, int value) {
        if (value < 1 || value > 9) {
            return false;
        }

        // Row Check
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == value) {
                return false;
            }
        }

        // Column Check
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == value) {
                return false;
            }
        }

        // Subgrid Check
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (grid[i][j] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public void print() {
        for (int i = 0; i < 0; i++) {
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
}
