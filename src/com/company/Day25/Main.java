package com.company.Day25;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        long[] instructions = parse();
        Intcode intcode = new Intcode(instructions);
        intcode.ASCIIMode(true);
        int code = intcode.run();
        while (code == 3) {
            code = intcode.input(sc.nextLine());
        }
        // Items to ignore: Photon, Giant Electromagnet, Infinite loop, Molten lava, Escape pod
        // Idea: List all inventory, then somehow list all combinations, then convert it to some sort of binary shit
        System.out.println("Latest output: " + intcode.getOutput(-1));
    }

    public static long[] parse() throws FileNotFoundException {
        File file = new File("src\\com\\company\\Day25\\input.txt");
        Scanner sc = new Scanner(file);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        return instructions;
    }
}
