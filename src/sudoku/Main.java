package sudoku;

public class Main {
    public static void main(String[] args) {
        SudokuGenerator gen = new SudokuGenerator();

        SudokuBoard puzzle = gen.generate(SudokuGenerator.Difficulty.MEDIUM);
        System.out.println("Puzzle:");
        puzzle.print();

        SudokuBoard solved = new SudokuBoard(puzzle.toArray());
        boolean solvable = SudokuSolver.solve(solved);

        System.out.println("\nSolved:");
        solved.print();

        System.out.println("\nSolvable? " + solvable);
        System.out.println("Solved correctly? " + solved.isSolved());
    }
}
