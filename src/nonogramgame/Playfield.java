package nonogramgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JPanel;

public class Playfield extends JPanel implements MouseListener {

    int nonogramWidth = 0;
    int nonogramHeight = 0;

    int[][] nonogram;

    public Playfield(int windowWidth, int windowHeight) throws IOException {

        setPreferredSize(new Dimension(windowWidth, windowHeight));
        addMouseListener(this);
    }

    public int[][] getNonogram() {
        return nonogram;
    }

    @Override
    public void paint(Graphics g) {
        Dimension window = getPreferredSize();
        g.fillRect(0, 0, window.width, window.height);
        if (nonogramWidth != 0) {
            for (int i = 0; i < nonogramWidth; i++) {
                for (int j = 0; j < nonogramHeight; j++) {
                    switch (nonogram[i][j]) {
                        case 0:
                            g.setColor(Color.WHITE);
                            break;
                        case 1:
                            g.setColor(Color.BLACK);
                            break;
                        case 2:
                            g.setColor(Color.CYAN);
                            break;
                        default:
                            throw new AssertionError();
                    }
                    g.fillRect((int) NonogramFrame.squareSize * i, NonogramFrame.squareSize * j, NonogramFrame.squareSize, NonogramFrame.squareSize);
                }
            }
        }

        //draws the grid
        for (int i = 0; i < Math.max(nonogramWidth, nonogramHeight); i++) {
            if (i % 5 == 0) {
                g.setColor(Color.DARK_GRAY);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            
            g.drawLine((int) (i * NonogramFrame.squareSize), 0, (int) (i * NonogramFrame.squareSize), window.height);
            g.drawLine(0, (int) (i * NonogramFrame.squareSize), window.width, (int) (i * NonogramFrame.squareSize));
        }
    }

    public void setNonogramSize(int width, int height) {
        nonogramWidth = width;
        nonogramHeight = height;

        nonogram = new int[nonogramWidth][nonogramHeight];
        repaint();
    }

    int startX;
    int startY;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Dimension window = getPreferredSize();
        
        startX = e.getX() * nonogramWidth / window.width;
        startY = e.getY() * nonogramHeight / window.height;
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Dimension window = getPreferredSize();
        int endX = e.getX() * nonogramWidth / window.width;
        int endY = e.getY() * nonogramHeight / window.height;
        
        if (endX < startX) {
            int temp = endX;
            endX = startX;
            startX = temp;
        }
        
        if (endY < startY) {
            int temp = endY;
            endY = startY;
            startY = temp;
        }
        
        int type = 0;
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                type = 1;
                break;
            case MouseEvent.BUTTON2:
                type = 2;
                break;
            default:
                type = 0;
                break;
        }
        
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                nonogram[i][j] = type;
            }
        }
        
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
