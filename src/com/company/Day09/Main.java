package com.company.Day09;

import java.io.File;
import java.io.FileNotFoundException;
import com.company.Intcode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day09\\input.txt");
        Scanner sc = new Scanner(file);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        Intcode intcode = new Intcode(instructions);
        long result = intcode.run();

        System.out.println("Program terminated with the code: " + result);
    }
}
