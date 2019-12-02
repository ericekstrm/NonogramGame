package nonogramgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JPanel;

public class VerticalBar extends JPanel {

    int rows = 0;
    int width = 0;

    int[][] barNumbers;

    public VerticalBar(int width, int height) {

        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paint(Graphics g) {
        Dimension window = getPreferredSize();
        //g.setColor(Color.LIGHT_GRAY);
        //g.fillRect(0, 0, window.width, window.height);
        //g.setColor(Color.BLACK);
        //g.drawRect(0, 0, window.width - 1, window.height);

        if (rows != 0) {

            int sSize = NonogramFrame.squareSize;

            //draw horizontal lines
            g.setColor(Color.black);
            for (int i = 0; i < rows; i++) {
                g.drawLine(0, i * sSize, window.width, i * sSize);
            }

            //draw vertical lines
            for (int i = 0; i < width; i++) {
                g.drawLine(i * sSize, 0, i * sSize, window.height);
            }

            for (int iy = 0; iy < rows; iy++) {
                for (int ix = 0; ix < barNumbers[iy].length; ix++) {
                    String s = Integer.toString(barNumbers[iy][ix]);
                    g.drawString(s, ix * sSize + 2 + window.width - barNumbers[iy].length * sSize, (iy + 1) * sSize - 2);
                }
            }
        }
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void generateBarNumbers(int[][] nonogram) {
        nonogram = transpose(nonogram);
        barNumbers = new int[nonogram.length][];
        for (int iy = 0; iy < nonogram.length; iy++) {
            int[] row = nonogram[iy];
            String rowNumbers = "";

            int blackCounter = 0;
            for (int ix = 0; ix < row.length; ix++) {
                if (blackCounter != 0 && row[ix] == 0) {
                    rowNumbers += blackCounter + ",";
                    blackCounter = 0;

                } else if (row[ix] == 1) {
                    blackCounter++;
                }
            }
            if (blackCounter != 0) {
                rowNumbers += blackCounter + ",";
            }

            String[] strings = rowNumbers.split(",");
            barNumbers[iy] = Arrays.asList(strings).stream().mapToInt(Integer::parseInt).toArray();
        }
        repaint();

        //find the max width of the bar
        width = 0;
        for (int[] barNumber : barNumbers) {
            if (barNumber.length > width) {
                width = barNumber.length;
            }
        }
    }

    public int[][] transpose(int[][] array) {
        if (array == null || array.length == 0)//empty or unset array, nothing do to here
        {
            return array;
        }

        int width = array.length;
        int height = array[0].length;

        int[][] array_new = new int[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array_new[y][x] = array[x][y];
            }
        }
        return array_new;
    }

    /*
    * Sets the preferred size of the bar based on the row with the most
    * numbers.
    * Returns its prefered width.
     */
    int setPreferredSize() {
        setPreferredSize(new Dimension(width * NonogramFrame.squareSize, rows * NonogramFrame.squareSize));
        return width * NonogramFrame.squareSize;
    }
}
