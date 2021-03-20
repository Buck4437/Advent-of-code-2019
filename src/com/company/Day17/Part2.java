package com.company.Day17;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part2 {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day17\\input2.txt");
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

        for (String str : new String[]{
                "A,A,B,C,B,C,B,C,B,A", // Main
                "R,10,L,12,R,6", // A
                "R,6,R,10,R,12,R,6", // B
                "R,10,L,12,L,12", // C
                "n" // Continuous
        }) {
            code = intcode.input(str);
        }

//        while (code == 3) {
//            code = intcode.input(sc2.nextLine());
//        }
        System.out.println("Dust collected: " + intcode.getOutput(-1));
        System.out.println("Program terminated with the code: " + code);

    }
}
