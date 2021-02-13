package com.company.Day01;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day01\\input.txt");
        Scanner sc = new Scanner(file);

        ArrayList<Integer> masses = new ArrayList<>();
        while (sc.hasNextLine()){
            int num = Integer.parseInt(sc.nextLine());
            masses.add(num);
        }
        double tot = 0;
        for (double mass : masses) {
            tot += getFuel(mass);
        }

        System.out.println(tot);
    }

    public static double getFuel(double mass) {
        double fuel = (Math.floor(mass/3) - 2);
        if (fuel <= 0) return 0;
        return fuel + getFuel(fuel);
    }

}
