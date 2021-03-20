package com.company.Day17;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Part1 {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day17\\input.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(System.in);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        Intcode intcode = new Intcode(instructions);
        intcode.ASCIIMode(true);
        int code = intcode.run();
        System.out.println("Program terminated with the code: " + code);
        String str = "";
        for (char c : intcode.getASCIIOutputs()) {
            str += c;
        }

        int sum = 0;

        for (int i = 1; i < str.split("\n").length - 1; i++) {
            String row = str.split("\n")[i];
            for (int j = 1; j < row.split("").length - 1; j++) {
                char up = str.split("\n")[i - 1].charAt(j);
                char down = str.split("\n")[i + 1].charAt(j);
                if (row.charAt(j - 1) == '#' && row.charAt(j) == '#' && row.charAt(j - 1) == '#' && up == '#' && down == '#') {
                    sum += i * j;
                }
            }
        }

        System.out.println(sum);
    }
}
