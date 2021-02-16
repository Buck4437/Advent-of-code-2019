package com.company.Day12;

public class Moon {

    int[] vel = {0, 0, 0};
    int[] pos;

    public Moon(int[] pos) {
        this.pos = pos.clone();
    }

    public void addVel(int[] acc) {
        for (int i = 0; i < 3; i++) {
            vel[i] += acc[i];
        }
    }

    public void updatePosition() {
        for (int i = 0; i < 3; i++) {
            pos[i] += vel[i];
        }
    }

    public long getEnergy() {
        long pot = 0, kin = 0;
        for (int i : pos) {
            pot += Math.abs(i);
        }
        for (int i : vel) {
            kin += Math.abs(i);
        }
        return pot * kin;
    }
}
