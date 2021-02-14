package com.company.Day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day06\\input.txt");
        Scanner sc = new Scanner(file);

        ArrayList<String> orbits = new ArrayList<>();
        while (sc.hasNextLine()){
            orbits.add(sc.nextLine());
        }
    }

}
