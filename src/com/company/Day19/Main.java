package com.company.Day19;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int x = 11, y = 17; // Hacky stuff

    static long[] instructions;

    public static void main(String[] args) throws FileNotFoundException {

        instructions = parse();

        while (true) {
            if (!run(x, y)) {
                if (x < y) x += 1;
                else y += 1;
            } else {
                boolean invalid = false;
                for (int i = 1; i < 100; i++) {
                    if (!run(x - i, y + i)) {
                        System.out.printf("oofed at %s, %s, %s\n", i, x, y);
                        invalid = true;
                        break;
                    }
                }
                if (invalid) {
                    x += 1;
                    while (true) {
                        if (run()) break;
                        y += 1;
                    }
                } else {
                    break;
                }
            }
        }

        System.out.println(x - 99 + ", " + y);
    }

    public static boolean run() {
        return run(instructions, x, y);
    }

    public static boolean run(int x, int y) {
        return run(instructions, x, y);
    }

    public static boolean run(long[] instructions, int x, int y) {
        Intcode intcode = new Intcode(instructions);

        intcode.reset();
        intcode.input(new long[]{x, y});
        return intcode.getOutput(-1) == 1;
    }

    public static long[] parse() throws FileNotFoundException {
        File file = new File("src\\com\\company\\Day19\\input.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(System.in);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        return instructions;
    }
}
