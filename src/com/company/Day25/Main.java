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
        String input = """
                north
                north
                take sand
                south
                south
                inv
                south
                take space heater
                south
                east
                take loom
                west
                north
                west
                take wreath
                south
                take space law space brochure
                south
                take pointer
                north
                north
                east
                north
                west
                west
                take festive hat
                east
                south
                take planetoid
                north
                west
                south
                west
                north
                inv""";

        String ans = gen_command(128 + 64 + 32 + 1);
        intcode.input(input);
        int counter = 0;
        while (code == 3) {
            String ln = sc.nextLine();
            if (ln.equals("n")) {
                ln = gen_command(counter);
                counter += 1;
            } else if (ln.equals("ans")) {
                ln = ans;
            }
            code = intcode.input(ln);
        }

        // Items to ignore: Photon, Giant Electromagnet, Infinite loop, Molten lava, Escape pod
        System.out.println("Latest output: " + intcode.getOutput(-1));
    }

    static String[] items = {
            "planetoids",
            "festive hat",
            "space heater",
            "loom",
            "space law space brochure",
            "sand",
            "pointer",
            "wreath"
    };

    private static String gen_command(int counter) {
        String command = "";
        for (String item : items) {
            command += "drop " + item + "\n";
            if (counter % 2 == 1) {
                command += "take " + item + "\n";
            }
            counter = (counter - counter % 2) / 2;
        }
        return command + "inv\nnorth";
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
