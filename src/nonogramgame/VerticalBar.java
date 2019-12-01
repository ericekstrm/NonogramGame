package nonogramgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JPanel;

public class VerticalBar extends JPanel {

    int rows = 0;

    int[][] barNumbers;

    public VerticalBar(int width, int height) {

        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paint(Graphics g) {
        Dimension window = getPreferredSize();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, window.width, window.height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, window.width - 1, window.height);

        if (rows != 0) {
            int verticalSize = window.height / rows;

            g.setColor(Color.black);
            for (int i = 0; i < rows; i++) {
                g.drawRect(0, i * verticalSize, window.width, verticalSize);
            }

            for (int iy = 0; iy < rows; iy++) {
                for (int ix = 0; ix < barNumbers[iy].length; ix++) {
                    g.drawRect(ix*NonogramFrame.squareSize, iy * verticalSize, NonogramFrame.squareSize, NonogramFrame.squareSize);
                    g.drawString(Integer.toString(barNumbers[iy][ix]), ix * NonogramFrame.squareSize + 1, iy * verticalSize + NonogramFrame.squareSize);
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

    void setPreferredSize() {
        int width = 0;
        for (int[] barNumber : barNumbers) {
            if (barNumber.length > width) {
                width = barNumber.length;
            }
        }
        setPreferredSize(new Dimension(width * NonogramFrame.squareSize, rows * NonogramFrame.squareSize));
    }
}
