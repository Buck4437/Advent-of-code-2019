package com.company.Day12;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return "Moon{" +
                "vel=" + Arrays.toString(vel) +
                ", pos=" + Arrays.toString(pos) +
                '}';
    }
}
