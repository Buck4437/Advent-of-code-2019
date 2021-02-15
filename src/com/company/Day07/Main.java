package com.company.Day07;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day07\\input.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(System.in);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        Intcode[] intcodes = new Intcode[5];

        for (int i = 0; i < 5; i++) {
            intcodes[i] = new Intcode(instructions);
        }

        ArrayList<int[]> combos = heap(new int[]{5, 6, 7, 8, 9});

        long max = 0;
        int[] best = new int[0];

        for (int[] combo : combos) {

            for (Intcode intcode : intcodes) {
                intcode.reset();
            }

            long signal = 0;
            boolean stop = false;
            boolean firstRun = true;

            while (!stop) {
                for (int i = 0; i < 5; i++) {
                    Intcode intcode = intcodes[i];

                    System.out.println("Running intcode computer " + (i + 1));

                    if (firstRun) {
                        intcode.input(combo[i]);
                    }

                    int code = intcode.input(signal);

                    signal = intcode.getOutput(-1);

                    if (code == 0 && i == 4) {
                        stop = true;
                        break;
                    }
                }
                
                firstRun = false;
            }

            long thruster = intcodes[4].getOutput(-1);

            System.out.println(Arrays.toString(combo) + ": " + thruster);
            if (thruster > max) {
                max = thruster;
                best = combo;
            }
        }

        System.out.println("Best: " +  Arrays.toString(best) + " with strength of " + max);


    }

    public static ArrayList<int []> heap(int [] input) {
        ArrayList<int []> ret = new ArrayList<int []> ();
        ret = generate(input.length, input, ret);
        return ret;
    }

    public static ArrayList<int []> generate(int k, int [] a, ArrayList<int []> output) {
        if (k == 1) {
            output.add(a.clone());
        } else {
            output = generate(k-1, a, output);
            for (int i=0; i<k-1; i++) {
                if (k%2 == 0) {
                    int temp = a[i];
                    a[i] = a[k-1];
                    a[k-1] = temp;
                } else {
                    int temp = a[0];
                    a[0] = a[k-1];
                    a[k-1] = temp;
                }
                generate(k-1, a, output);
            }
        }
        return output;
    }


}
