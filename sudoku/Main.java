package sudoku;

public class Main {
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard();
        board.forceSet(0,0,3);
        board.print();
        System.out.println(board.isLegalMove(0, 1, 5));
        System.out.println(board.isLegalMove(0, 1, 3));
    }
}
