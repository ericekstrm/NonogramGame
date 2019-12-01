package nonogramgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.JPanel;

public class HorizontalBar extends JPanel {

    int columns = 0;
    int[][] barNumbers;

    public HorizontalBar(int width, int height) {

        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paint(Graphics g) {
        Dimension window = getPreferredSize();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, window.width, window.height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, window.width, window.height - 1);
        if (columns != 0) {
            int horizontalSize = window.width / columns;

            g.setColor(Color.black);

            for (int iy = 0; iy < columns; iy++) {
                
                for (int ix = 0; ix < barNumbers[iy].length; ix++) {
                    g.drawRect(iy * horizontalSize, ix*NonogramFrame.fontSize, NonogramFrame.squareSize, NonogramFrame.fontSize);
                    g.drawString(Integer.toString(barNumbers[iy][ix]), iy * horizontalSize + 1, ix * NonogramFrame.fontSize + NonogramFrame.fontSize);
                }
            }
        }
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void generateBarNumbers(int[][] nonogram) {
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

    public void setPreferredSize() {
        int height = 0;
        for (int[] barNumber : barNumbers) {
            if (barNumber.length > height) {
                height = barNumber.length;
            }
        }
        setPreferredSize(new Dimension(columns * NonogramFrame.squareSize, height * NonogramFrame.fontSize));
    }
}
