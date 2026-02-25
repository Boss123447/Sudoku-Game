package sudoku;

public class SudokuSolver {
    public boolean solve(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.get(row, col) == 0) {
                    for ( int num = 1; num <= 9; num++) {
                        if (board.isLegalMove(row, col, num)) {
                            board.set(row, col, num);
                            
                            if (solve(board)) {
                                return true;
                            }
                            
                            board.set(row, col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
