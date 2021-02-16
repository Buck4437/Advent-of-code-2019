package com.company.Day13;

import javax.swing.*;
import java.util.Hashtable;

public class MyFrame extends JFrame {

    Graphics graphics;
    Hashtable<String, Integer> tiles;

    public MyFrame(Hashtable<String, Integer> tiles) {
        this.setTitle("Intcode");
        this.setSize(550, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.tiles = tiles;
        graphics = new Graphics(tiles);
        this.add(graphics);

        this.setVisible(true);
    }

    public void draw() {
        graphics.repaint();
    }

}
