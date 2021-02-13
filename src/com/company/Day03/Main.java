package com.company.Day03;

import com.company.Day02.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day04\\input.txt");
        Scanner sc = new Scanner(file);

        Wire wire1 = new Wire(sc.nextLine());
        Wire wire2 = new Wire(sc.nextLine());

        System.out.println("Part 1: \n");
        System.out.println(wire1.intersect(wire2));

        System.out.println();

        System.out.println("Part 2: \n");
        System.out.println(wire1.steps(wire2));

    }

}
