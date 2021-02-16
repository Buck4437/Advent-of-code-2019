package com.company.Day12;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        long time = System.nanoTime();

        ArrayList<Moon> moons = new ArrayList<>();
//        moons.add(new Moon(new int[]{-7, -1, 6}));
//        moons.add(new Moon(new int[]{6, -9, -9}));
//        moons.add(new Moon(new int[]{-12, 2, -7}));
//        moons.add(new Moon(new int[]{4, -17, -12}));

        moons.add(new Moon(new int[]{-8, -10, 0}));
        moons.add(new Moon(new int[]{5, 5, 10}));
        moons.add(new Moon(new int[]{2, -7, 3}));
        moons.add(new Moon(new int[]{9, -8, -3}));

        for (int steps = 0; steps < 10000000; steps++) {
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
        }

        long energy = 0;
        for (Moon moon : moons) {
            energy += moon.getEnergy();
        }

        System.out.println(energy);
        System.out.println("Computation time: " + (System.nanoTime() - time)/1e6 + " ms");

    }

}
