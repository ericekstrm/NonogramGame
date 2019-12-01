package nonogramgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class OverviewImage extends JPanel {

    public OverviewImage(int width, int height) {
        
        setPreferredSize(new Dimension(width, height));
    }
    
    @Override
    public void paint(Graphics g) {
        Dimension d = getPreferredSize();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, d.width, d.height);
    }
}
