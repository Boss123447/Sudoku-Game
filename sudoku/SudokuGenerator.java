package sudoku;

import java.util.*;

public class SudokuGenerator {
    private final Random random = new Random();

    public enum Difficulty {
        EASY(35),
        MEDIUM(28),
        HARD(22);

        private final int clues;
        Difficulty(int clues) { 
            this.clues = clues;
        }

        public int getClues() {
            return clues;
        }
    }

    public SudokuBoard generate(Difficulty difficulty) {
        SudokuBoard board = new SudokuBoard();
        fillBoard(board);
        removeCells(board, difficulty.getClues());
        return board;
    }

    private void fillBoard(SudokuBoard board) {
        for (int i = 0; i < 9; i += 3) {
            fillBox(board, i, i);
        }

        SudokuSolver.solve(board);
    }

    private void removeCells(SudokuBoard board, int clues) {
        List<int[]> cells = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells.add(new int[]{r, c});
            }
        }

        Collections.shuffle(cells, random);
        int filled = 81;

        for (int [] cell : cells) {
            if (filled <= clues) {
                break;
            }

            int r = cell[0];
            int c = cell[1];

            int backup = board.get(r, c);
            board.forceSet(r, c, 0);

            SudokuBoard testCopy = new SudokuBoard(board.toArray());
            if (SudokuSolver.countSolutions(testCopy, 2) != 1) {
                board.forceSet(r, c, backup);
            } else {
                filled--;
            }
        }
    }

    private void fillBox(SudokuBoard board, int startRow, int startCol) {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            nums.add(i);
        }

        Collections.shuffle(nums, random);
        int index = 0;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                int value = nums.get(index++);
                board.forceSet(r, c, value);
            }
        }
    }
}
