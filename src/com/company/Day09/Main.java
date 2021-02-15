package com.company.Day09;

import java.io.File;
import java.io.FileNotFoundException;
import com.company.Intcode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day09\\input.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(System.in);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        Intcode intcode = new Intcode(instructions);
        int code = intcode.run();
        while (code == 3) {
            code = intcode.input(sc2.nextLong());
        }

        System.out.println("Program terminated with the code: " + code);
    }
}
