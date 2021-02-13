package com.company.Day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day02\\input.txt");
        Scanner sc = new Scanner(file);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        Intcode intcode;

        for (int i = 0; i < 100; i++) {
            instructions[1] = i;
            for (int j = 0; j < 100; j++) {
                instructions[2] = j;

                intcode = new Intcode(instructions);

                long result = intcode.run();

                if (result == 19690720) {
                    System.out.println(100 * i + j);
                    return;
                }

            }
        }


    }
}
