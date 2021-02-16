package com.company.Day13;

import java.awt.*;
import java.util.Hashtable;
import javax.swing.*;

public class Graphics extends JPanel {

    private Hashtable<String, Integer> tiles;

    public Graphics(Hashtable<String, Integer> tiles) {
        super();
        this.tiles = tiles;
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {

        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        Graphics2D g2D = (Graphics2D) g;


        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 45; x++) {
                if (tiles.containsKey(coordToString(x, y))) {
                    switch (tiles.get(coordToString(x, y))) {
                        case 0 -> {
                            g2D.setColor(Color.BLACK);
                            g2D.fillRect(50 + x * 10, 50 + y * 10, 50, 50);
                        }
                        case 1 -> {
                            g2D.setColor(Color.BLUE);
                            g2D.fillRect(50 + x * 10, 50 + y * 10, 50, 50);
                        }
                        case 2 -> {
                            g2D.setColor(Color.WHITE);
                            g2D.fillRect(50 + x * 10, 50 + y * 10, 50, 50);
                        }
                        case 3 -> {
                            g2D.setColor(Color.RED);
                            g2D.fillRect(50 + x * 10, 50 + y * 10, 50, 50);
                        }
                        case 4 -> {
                            g2D.setColor(Color.GREEN);
                            g2D.fillRect(50 + x * 10, 50 + y * 10, 50, 50);
                        }
                    }
                } else {
                    g2D.setColor(Color.BLACK);
                    g2D.fillRect(50 + x * 10, 50 + y * 10,50, 50);
                }
            }
        }
        g2D.setColor(Color.BLUE);
        g2D.drawString("Score: " + tiles.get(coordToString(-1, 0)), 30, 350);


//        g2D.setStroke(new BasicStroke(5));
//        g2D.drawLine(0, 0, 400, 400);

//        g2D.setFont(new Font("Comic Sans", Font.ITALIC, 20));
//        g2D.setColor(Color.BLUE);
//        g2D.drawString("Score: ", 30, 550);

//        g2D.fillRect(0, 0, 100, 100);

    }

    public String coordToString(int x, int y) {
        return x + ", " + y;
    }

}
