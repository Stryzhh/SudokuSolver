import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Solver.java.
 * @version 1.0.0
 * This class reads a sudoku from testcases and uses recursive backtracking
 * to try and find a working solution to the sudoku.
 * @author Dafydd-Rhys Maund
 */
public class Solver extends JFrame
{

    /**
     * properties of a sudoku board
     * -EMPTY if the current cell is empty
     * -SIZE the parameter of the puzzle [9x9]
     * -SQUARE_PARAMETER the parameter of a square block [3x3]
     * -SUDOKU_BOARD the original puzzle before being solved
     * -updatedBoard the solved puzzle
     */
    private static final int EMPTY = 0;
    private static final int SIZE = 9;
    private static final int SQUARE_PARAMETER = 3;
    private static final int[][] SUDOKU_BOARD = new int[SIZE][SIZE];
    private static int[][] updatedBoard;

    /**
     * this main method gets the file that is wanted to be tested and runs it
     * through the algorithm, if its solvable it creates a visual image of the
     * solved puzzle, otherwise outputs its unsolvable
     */
    public static void main(final String[] args) throws FileNotFoundException
    {
        File directory = new File("tests\\");
        int fileCount = Objects.requireNonNull(directory.list()).length;
        int testNumber = 0;

        System.out.print("Enter the Sudoku test you wish to run (1-" + fileCount + "): ");
        Scanner input = new Scanner(System.in);
        if (input.hasNextInt())
        {
            testNumber = input.nextInt();
        }
        if (testNumber > fileCount || testNumber < 1)
        {
            System.out.print("Input Invalid.");
            System.exit(0);
        }

        File test = new File("tests\\test-case" + testNumber + ".txt");
        getSudokuPuzzle(test);

        //creating frame to visualise the solved sudoku
        JFrame frame = new JFrame();
        frame.setTitle("SudokuSolverVisualisation");
        frame.setSize(370,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCentre(frame);

        //creates board that's wanted to be solved
        Solver sudokuBoard = new Solver(SUDOKU_BOARD);
        System.out.println("\nOriginal Sudoku:\n");
        sudokuBoard.printBoard();

        if (sudokuBoard.solveBoard())
        {
            System.out.println("\nSolved Sudoku:\n");
            sudokuBoard.printBoard();
            frame.setVisible(true);
            Draw solvedSudokuVisual = new Draw(SUDOKU_BOARD, updatedBoard);
            frame.add(solvedSudokuVisual);
        }
        else
        {
            System.out.println("\nSudoku is unsolvable");
            frame.setVisible(false);
            failedSolve();
        }
    }

    /**
     * @param boardToSolve the original unsolved sudoku
     * this method copies the original the original unsolved sudoku to the updated board
     * so we can distinguish between the unsolved and solved puzzle arrays
     */
    public Solver(final int[][] boardToSolve)
    {
        updatedBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
        {
            System.arraycopy(boardToSolve[i], 0, updatedBoard[i], 0, SIZE);
        }
    }

    /**
     * @param file the testcase
     * this method reads and fills the testcases' sudoku puzzle into an array
     */
    public static void getSudokuPuzzle(File file) throws FileNotFoundException
    {
        Scanner sc = new Scanner(file);
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                SUDOKU_BOARD[i][j] = sc.nextInt();
            }
        }
    }

    /**
     * this method creates the visual for when a puzzle isn't solvable
     */
    public static void failedSolve()
    {
        JFrame frame = new JFrame();
        frame.setTitle("SudokuSolverVisualisation");
        frame.setSize(370,400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setCentre(frame);

        Draw solvedSudokuVisual = new Draw(SUDOKU_BOARD, SUDOKU_BOARD);
        frame.add(solvedSudokuVisual);
    }


    /**
     * @param frame the frame which displays the visual of the Sudoku's
     * this method centres the frame to the middle the users monitor
     */
    public static void setCentre(JFrame frame)
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2 - frame.getSize().width / 2,
                dim.height/2 - frame.getSize().height/2);
    }

    /**
     * this method runs numbers into the available cells, and checks whether they are
     * suitable, if not, a new number is tried. If there is no possible more combinations
     * of numbers to be used then the puzzle is considered unsolveable. This process continues
     * until a correct combination is achieved.
     * @return whether the board is solved
     */
    public final boolean solveBoard()
    {
        for (int row = 0; row < SIZE; row++)
        {
            for (int col = 0; col < SIZE; col++)
            {
                if (updatedBoard[row][col] == EMPTY)
                {
                    for (int number = 1; number <= SIZE; number++)
                    {
                        if (isNumberOk(row, col, number))
                        {
                            updatedBoard[row][col] = number;
                            if (solveBoard())
                            {
                                return true;
                            }
                            else
                            {
                                updatedBoard[row][col] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * this method simply prints the solved board in text
     */
    public final void printBoard()
    {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                System.out.print(" " + updatedBoard[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * @param number the number that's being tested
     * @param row the row the number is on
     * @param col the column the number is on
     * this method checks if the number is ok to be used, this is decided by
     * checking multiple rules incl. row, column and box restraints
     * @return depending on whether number is suitable
     */
    private boolean isNumberOk(final int row, final int col, final int number)
    {
        return !isInRow(row, number)  &&  !isInCol(col, number)
                &&  !isInBox(row, col, number);
    }

    /**
     * @param number the number that's being tested
     * @param row the row the number is on
     * @param col the column the number is on
     * this method checks if the number already exists within its box, if it does the
     * number isn't suitable
     * @return depending on whether number is suitable
     */
    private boolean isInBox(final int row, final int col, final int number)
    {
        int r = row - row % SQUARE_PARAMETER;
        int c = col - col % SQUARE_PARAMETER;

        for (int i = r; i < r + SQUARE_PARAMETER; i++)
        {
            for (int j = c; j < c + SQUARE_PARAMETER; j++)
            {
                if (updatedBoard[i][j] == number)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param number the number that's being tested
     * @param col the column the number is on
     * this method checks if the number already exists within its column, if it does the
     * number isn't suitable
     * @return depending on whether number is suitable
     */
    private boolean isInCol(final int col, final int number)
    {
        for (int i = 0; i < SIZE; i++)
        {
            if (updatedBoard[i][col] == number)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param number the number that's being tested
     * @param row the row the number is on
     * this method checks if the number already exists within its row, if it does the
     * number isn't suitable
     * @return depending on whether number is suitable
     */
    private boolean isInRow(final int row, final int number)
    {
        for (int i = 0; i < SIZE; i++)
        {
            if (updatedBoard[row][i] == number)
            {
                return true;
            }
        }
        return false;
    }
}
