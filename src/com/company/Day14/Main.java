package com.company.Day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day14\\input.txt");
        Scanner sc = new Scanner(file);

        ArrayList<String> str = new ArrayList<>();
        while (sc.hasNextLine()) {
            str.add(sc.nextLine());
        }
        str.add("1 BUFFER => 1 ORE");

        Factory factory = new Factory(str);
        long testData = 1;
        long scaling = 10000000;
        while (true) {
            System.out.println(testData);
            factory.reset();
            factory.addItem("BUFFER", Long.MAX_VALUE);
            factory.addItem("ORE", (long) 1e12);
            factory.craftItem("FUEL", testData);
            if (factory.getItem("BUFFER") < Long.MAX_VALUE) {
                if (scaling == 1) {
                    testData -= scaling;
                    break;
                }
                testData -= scaling;
                scaling /= 10;
            } else {
                testData += scaling;
            }
        }
        System.out.println(testData);
    }

}
