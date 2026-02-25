package sudoku;

public class SudokuSolver {

    public static boolean solve(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.get(row, col) == 0) {
                    for ( int num = 1; num <= 9; num++) {
                        if (board.isLegalMove(row, col, num)) {
                            board.forceSet(row, col, num);
                            if (solve(board)) {
                                return true;
                            }
                            board.forceSet(row, col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static int countSolutions(SudokuBoard board, int limit) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.get(row, col) == 0) {
                    int count = 0;

                    for (int num = 1; num <= 9; num++) {
                        if (board.isLegalMove(row, col, num)) {
                            board.forceSet(row, col, num);
                            count += countSolutions(board, limit);
                            board.forceSet(row, col, 0);

                            if (count >= limit){
                                return count;
                            }
                        }
                    }

                    return count;
                }
            }
        }

        return 1;
    }
}
