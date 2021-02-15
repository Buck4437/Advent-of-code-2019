package com.company.Day08;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day08\\input.txt");
        Scanner sc = new Scanner(file);

        String encoded = sc.nextLine();
        ArrayList<Layer> layers = new ArrayList<>();

        int min = Integer.MAX_VALUE, ans = 0;
        String tmp = "";
        for (char c : encoded.toCharArray()) {
            tmp += c;
            if (tmp.length() >= 25 * 6) {
                Layer layer = new Layer(tmp);
                if (layers.size() != 0) {
                    layers.get(layers.size() - 1).setNextLayer(layer);
                }
                layers.add(layer);
                tmp = "";
            }
        }

        System.out.println(layers.get(0).getImage());
    }

}
