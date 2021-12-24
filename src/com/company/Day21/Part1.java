package com.company.Day21;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Part1 {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day21\\input.txt");
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

        String str = """
                    OR A T
                    AND B T
                    AND C T
                    AND D T
                    NOT T T
                    OR D J
                    AND T J
                    WALK""";

        code = intcode.input(str);

//        while (code == 3) {
//            code = intcode.input(sc2.nextLine());
//        }
        System.out.println();
        System.out.println("Damage: " + intcode.getOutput(-1));
        System.out.println("Program terminated with the code: " + code);

    }
}
