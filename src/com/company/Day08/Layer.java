package com.company.Day08;

import java.util.ArrayList;

public class Layer {

    String encoded;
    Layer nextLayer = null;

    public Layer(String s) {
        encoded = s;
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public String getImage() {
        String decoded = "";
        ArrayList<String> image = new ArrayList<>();
        for (int i = 0; i < encoded.length(); i++) {
            decoded += getPixel(i);
            if (decoded.length() >= 25) {
                image.add(decoded);
                decoded = "";
            }
        }
        return String.join("\n", image);
    }

    private char getPixel(int pos) {
        char pixel = encoded.toCharArray()[pos];
        return switch (pixel) {
            case '0' -> ' ';
            case '1' -> '#';
            default -> nextLayer.getPixel(pos);
        };
    }
}
