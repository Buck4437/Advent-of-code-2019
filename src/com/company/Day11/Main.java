package com.company.Day11;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day11\\input.txt");
        Scanner sc = new Scanner(file);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        Intcode intcode = new Intcode(instructions);

        HashSet<String> white = new HashSet<>();
        white.add("[0, 0]");

        int[] coord = {0, 0};
        int[] dir = {0, 1}; //right = x, up = y

        int code = intcode.run();
        while (code == 3) {
            code = intcode.input(white.contains(Arrays.toString(coord)) ? 1 : 0);
            if (code != 0) {
                if (intcode.getOutput(-2) == 0) {
                    white.remove(Arrays.toString(coord));
                } else {
                    white.add(Arrays.toString(coord));
                }

                if (intcode.getOutput(-1) == 0) { //left
                    dir = new int[]{-dir[1], dir[0]};
                } else { //right
                    dir = new int[]{dir[1], -dir[0]};
                }
                coord[0] += dir[0];
                coord[1] += dir[1];
            }
        }

        System.out.println("Program terminated with the code: " + code);

        String img = "";
        for (int j = 0; j > -6; j--) {
            for (int i = 0; i < 40; i++) {
                img += (white.contains(Arrays.toString(new int[]{i, j})) ? "#" : " ");
            }
            img += "\n";
        }

        System.out.println(img);
    }
}
