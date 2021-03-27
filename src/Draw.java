import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

/**
 * Draw.java.
 * @version 1.0.0
 * Draws the unsolved and solved sudoku's
 * @author Dafydd-Rhys Maund
 */
public final class Draw extends JPanel
{
    /**
     * properties of a sudoku board and frame
     * -SUDOKU_BOARD the original puzzle before being solved
     * -updatedBoard the solved puzzle
     * -currentWidth the width on the frame
     * -currentHeight the height on the frame
     */
    private final int[][] board; //9x9 board
    private final int[][] updatedBoard; //9x9 solved board
    private int currentWidth; //width on frame
    private int currentHeight; //height on frame

    /**
     * paints the unsolved sudoku and the solved sudoku if it has
     * been solved
     * @param g the graphic used to draw the items
     */
    public final void paint(final Graphics g)
    {
        if (board == updatedBoard)
        {
            Font titleFont = new Font("Serif", Font.PLAIN, 20);
            Font sudokuFont = new Font("Serif", Font.PLAIN, 12);
            printFirstLoop(board, g, titleFont, sudokuFont);

            g.setFont(titleFont);
            g.drawString("The Sudoku is unsolvable...", currentWidth + 30,
                    currentHeight - 25);
            g.setFont(sudokuFont);
        }
        else
        {
            Font titleFont = new Font("Serif", Font.PLAIN, 20);
            Font sudokuFont = new Font("Serif", Font.PLAIN, 12);
            printFirstLoop(board, g, titleFont, sudokuFont);

            g.setFont(titleFont);
            g.drawString("The Solved Sudoku Puzzle:", currentWidth,
                    currentHeight - 10);
            g.setFont(sudokuFont);
            loopArrays(updatedBoard, g);
        }
    }

    /**
     * this method simply prints the unsolved sudoku
     * @param g graphic used to draw visuals
     * @param originalBoard the original sudoku's values
     * @param sudokuFont the font used in the sudoku
     * @param titleFont the font used for the titles
     */
    public void printFirstLoop(final int[][] originalBoard, final Graphics g,
                                     final Font titleFont, final Font sudokuFont)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        setCurrentHeight(50);
        setCurrentWidth(42);

        g.setFont(titleFont);
        g.drawString("The Original Sudoku Puzzle:", currentWidth,
                currentHeight - 10);
        g.setFont(sudokuFont);

        loopArrays(originalBoard, g);
        setCurrentHeight(370);
    }

    /**
     * this method loops through whatever array is passed through
     * and visualises it using a graphic
     * @param currentBoard the board being passed
     * @param g graphic used to draw visuals
     */
    public void loopArrays(final int[][] currentBoard, final Graphics g)
    {
        //board parameter
        int size = 9;
        for (int i = 0; i < size; i++)
        {
            g.drawRect(currentWidth, currentHeight, 30, 30);
            for (int j = 0; j < size; j++)
            {
                g.drawRect(currentWidth, currentHeight, 30, 30);
                String value = String.valueOf(currentBoard[i][j]);
                if (value.equals("0"))
                {
                    g.drawString(value, currentWidth + 13, currentHeight + 20);
                }
                else
                {
                    g.setColor(Color.RED);
                    g.drawString(value, currentWidth + 13, currentHeight + 20);
                    g.setColor(Color.BLACK);
                }
                currentWidth += 30;
            }
            setCurrentHeight(currentHeight += 30);
            setCurrentWidth(42);
        }
    }

    /**
     * this method is a constructor used to update the boards being drawn
     * @param oldBoard the unsolved board being passed
     * @param newBoard the solved board being passed
     */
    public Draw(final int[][] oldBoard, final int[][] newBoard)
    {
        this.board = oldBoard;
        this.updatedBoard = newBoard;
    }

    /**
     * this method set the currentHeight variable with a new value
     * @param newCurrentHeight the unsolved board being passed
     */
    public void setCurrentHeight(final int newCurrentHeight)
    {
        this.currentHeight = newCurrentHeight;
    }

    /**
     * this method set the currentWidth variable with a new value
     * @param newCurrentWidth the unsolved board being passed
     */
    public void setCurrentWidth(final int newCurrentWidth)
    {
        this.currentWidth = newCurrentWidth;
    }
}
