package com.company.Day12;


import java.util.ArrayList;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {

        long time = System.nanoTime();

        Hashtable<String, Long> states = new Hashtable<>();
        ArrayList<Moon> moons = new ArrayList<>();

//        moons.add(new Moon(new int[]{-7, -1, 6}));
//        moons.add(new Moon(new int[]{6, -9, -9}));
//        moons.add(new Moon(new int[]{-12, 2, -7}));
//        moons.add(new Moon(new int[]{4, -17, -12}));

        moons.add(new Moon(new int[]{0, 0, 6}));
        moons.add(new Moon(new int[]{0, 0, -9}));
        moons.add(new Moon(new int[]{0, 0, -7}));
        moons.add(new Moon(new int[]{0, 0, -12}));

        // 186028, 84032, 231614 (Ans: 452582583272768)

        // Example

//        moons.add(new Moon(new int[]{-8, -10, 0}));
//        moons.add(new Moon(new int[]{5, 5, 10}));
//        moons.add(new Moon(new int[]{2, -7, 3}));
//        moons.add(new Moon(new int[]{9, -8, -3}));

        // 2028, 5898, 4702

        long steps = 0;
        long period;

        while (true) {
            String str = moons.toString();
            if (states.get(str) != null) {
                period = steps;
                break;
            } else {
                states.put(str, steps);
            }

            for (int i = 0; i < 4; i++) {
                for (int j = i + 1; j < 4; j++) {
                    int[] accI = {0, 0 ,0}, accJ = {0, 0, 0};
                    for (int k = 0; k < 3; k++) {
                        if (moons.get(i).pos[k] < moons.get(j).pos[k]) {
                            accI[k] = 1;
                            accJ[k] = -1;
                        }
                        if (moons.get(i).pos[k] > moons.get(j).pos[k]) {
                            accI[k] = -1;
                            accJ[k] = 1;
                        }
                    }
                    moons.get(i).addVel(accI);
                    moons.get(j).addVel(accJ);
                }
            }
            moons.forEach(Moon::updatePosition);
            steps ++;
        }

        System.out.printf("Period: %S\n", period);

        System.out.println("Computation time: " + (System.nanoTime() - time)/1e6 + " ms");

    }

}
