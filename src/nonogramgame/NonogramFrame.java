package nonogramgame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class NonogramFrame extends JFrame implements KeyListener{

    public static final int menuBarheight = 50;
    public static final int squareSize = 20;
    public static final int fontSize = 12;

    OverviewImage oImage;
    Playfield pField;
    VerticalBar vBar;
    HorizontalBar hBar;

    String nonogramName = "1.txt";
    int[][] nonogramAnswer;
    int nonogramWidth;
    int nonogramHeight;

    final int overviewWidth = 100;
    final int overviewHeight = 100;
    int playfieldWidth = squareSize * 10;
    int playfieldHeight = squareSize * 10;

    public NonogramFrame() throws IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());
        addKeyListener(this);
        addmenuBar();

        GridBagConstraints gbc = new GridBagConstraints();

        oImage = new OverviewImage(overviewWidth, overviewHeight);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(oImage, gbc);

        hBar = new HorizontalBar(playfieldWidth, overviewHeight);
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(hBar, gbc);

        vBar = new VerticalBar(overviewWidth, playfieldHeight);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(vBar, gbc);

        pField = new Playfield(playfieldWidth, playfieldHeight);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(pField, gbc);

        pack();
        setVisible(true);
    }

    public void run() {

        loadNonogram(nonogramName);
        playfieldWidth = squareSize * nonogramWidth;
        playfieldHeight = squareSize * nonogramHeight;
        
        pField.setNonogramSize(nonogramWidth, nonogramHeight);
        pField.setPreferredSize(new Dimension(playfieldWidth, playfieldHeight));
        
        vBar.setRows(nonogramHeight);
        vBar.generateBarNumbers(nonogramAnswer);
        vBar.setPreferredSize();
        
        hBar.setColumns(nonogramWidth);
        hBar.generateBarNumbers(nonogramAnswer);
        hBar.setPreferredSize();

        
        pack();
    }

    /*
    Nonograms saved on format (.txt):
    5x5
    101010
    101010
    101010
    101010
    101010
     */
    public void loadNonogram(String fileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("D:\\Downloads\\Nonogram\\" + fileName));
            String header = reader.readLine();
            nonogramWidth = Integer.parseInt(header.substring(0, header.indexOf("x")));
            nonogramHeight = Integer.parseInt(header.substring(header.indexOf("x") + 1));

            nonogramAnswer = new int[nonogramWidth][nonogramHeight];

            for (int i = 0; i < nonogramHeight; i++) {
                String line = reader.readLine();
                for (int j = 0; j < nonogramWidth; j++) {
                    nonogramAnswer[j][i] = Integer.parseInt(line.charAt(j) + "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addmenuBar() {
        JMenuBar menubar = new JMenuBar();

        File folder = new File("D:\\Downloads\\Nonogram");
        File[] listOfFiles = folder.listFiles();

        JMenu menu2 = new JMenu("Image file");
        for (File file : listOfFiles) {
            JMenuItem item = new JMenuItem(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nonogramName = e.getActionCommand();
                }
            });
            item.setText(file.getName());
            item.setActionCommand(file.getName());
            menu2.add(item);
        }
        menubar.add(menu2);

        JButton run = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
        run.setText("Play");
        menubar.add(run);

        JButton check = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean correct = nonogramCorrect();
                if (correct) {
                    System.out.println("Rätt!");
                } else {
                    System.out.println("Fel!");
                }
            }
        });
        check.setText("Check");
        menubar.add(check);

        setJMenuBar(menubar);
    }

    public boolean nonogramCorrect() {
        int[][] nonogramGuess = pField.getNonogram();

        for (int i = 0; i < nonogramWidth; i++) {
            for (int j = 0; j < nonogramHeight; j++) {
                if (nonogramAnswer[i][j] == 1 && nonogramGuess[i][j] != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {

        try {
            JFrame frame = new NonogramFrame();

        } catch (IOException ex) {
            System.out.println("Fel");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("hej");
        setVisible(false);
    }
}
