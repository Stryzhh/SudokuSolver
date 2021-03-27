import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * AddTestCase.java.
 * @version 1.0.0
 * adds a new testcase
 * @author Dafydd-Rhys Maund
 */
public class AddTestCase
{
    /**
     * properties of a sudoku board
     * -SIZE the size of 2d array
     * -SUDOKU_BOARD the new puzzle that is wanted to be solved
     */
    private static final int SIZE = 9;
    private static final int[][] SUDOKU_BOARD =
    {
        {3, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 2, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 5, 0, 0, 0, 0},
        {0, 0, 0, 0, 6, 0, 0, 0, 0},
        {0, 0, 0, 0, 8, 3, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 0},
        {0, 2, 1, 0, 0, 0, 4, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    /**
     * this main method simply adds a new testcase, using the last testcase
     * as a reference for the new testcases number
     */
    public static void main(final String[] args) throws IOException
    {
        File directory = new File("tests\\");
        int fileCount = Objects.requireNonNull(directory.list()).length;
        fileCount += 1;

        BufferedWriter myWriter = new BufferedWriter(new FileWriter
                ("tests\\test-case" + fileCount + ".txt", true));
        StringBuilder array = new StringBuilder();

        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                array.append(SUDOKU_BOARD[i][j]).append(" ");
            }
            array.append("\n");
        }

        myWriter.append(array);
        myWriter.close();
        System.out.println("Successfully created a new test case.");
    }
}
