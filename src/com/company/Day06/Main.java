package com.company.Day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src\\com\\company\\Day06\\input.txt");
        Scanner sc = new Scanner(file);

        HashMap<String, Obj> objects = new HashMap<>();

        while (sc.hasNextLine()){
            String[] orbit = sc.nextLine().split("\\)");
            Obj[] orbitObjects = new Obj[2];
            for (int i = 0; i <= 1; i++) {
                if (objects.get(orbit[i]) == null) {
                    orbitObjects[i] = new Obj(orbit[i]);
                    objects.put(orbit[i], orbitObjects[i]);
                } else {
                    orbitObjects[i] = objects.get(orbit[i]);
                }
            }
            orbitObjects[0].addChildren(orbitObjects[1]);
            orbitObjects[1].setParent(orbitObjects[0]);
        }

        for (Map.Entry<String, Obj> entry : objects.entrySet()) {
            if (!entry.getValue().hasParent()) {
                System.out.println(entry.getValue().getOrbits());
                break;
            }
        }

        HashSet<Obj> set = new HashSet<>();
        set.addAll(objects.get("YOU").getParents());
        set.addAll(objects.get("SAN").getParents());

        int A = objects.get("YOU").getParents().size();
        int B = objects.get("SAN").getParents().size();
        int AUB = set.size();

        System.out.println(AUB*2-A-B);
    }

}
