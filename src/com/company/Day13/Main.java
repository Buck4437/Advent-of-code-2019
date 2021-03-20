package com.company.Day13;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day13\\input.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(System.in);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        Hashtable<String, Integer> tiles = new Hashtable<>();
        MyFrame myFrame = new MyFrame(tiles);

        Intcode intcode = new Intcode(instructions);
        intcode.showLog(false);

        int code = 1;
        int input = 0;
        do {
            intcode.resetOutput();

            if (code == 1) {
                code = intcode.run();
            } else {
                code = intcode.input(input);
            }

            int ball = 0, paddle = 0;

            for (int i = 0; i <= (intcode.getOutputs().size() - 1) / 3; i++) {
                int x = (int) intcode.getOutput(i * 3);
                int y = (int) intcode.getOutput(i * 3 + 1);
                int type = (int) intcode.getOutput(i * 3 + 2);

                if (type == 3) {
                    paddle = x;
                } else if (type == 4) {
                    ball = x;
                }

                tiles.put(coordToString(x, y), type);
            }

            myFrame.draw();

            input = Integer.compare(ball, paddle);

            long time = System.nanoTime();
            while (true) {
                if ((System.nanoTime() - time)/1e6 > 30) break;
            }

        } while (code != 0);

        System.out.println("Program terminated with the code: " + code);

    }

    public static String coordToString(int x, int y) {
        return x + ", " + y;
    }
}
