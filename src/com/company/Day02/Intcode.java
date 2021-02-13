package com.company.Day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Intcode {

    ArrayList<Long> memory = new ArrayList<>();
    int pointer = 0;

    public Intcode(long[] memory) {
        for (long mem : memory) {
            this.memory.add(mem);
        }
    }

    public long run() {
        while (pointer < memory.size()) {
            int opcode = (int) getValue(pointer);
            long[] params;

            System.out.println(memory);

            System.out.println("Running opcode " + opcode + " at position " + pointer);

            switch (opcode) {
                case 1:
                    params = getParameters(3);
                    setValue(params[2], getValue(params[0]) + getValue(params[1]));
                    pointer += 4;
                    break;
                case 2:
                    params = getParameters(3);
                    setValue(params[2], getValue(params[0]) * getValue(params[1]));
                    pointer += 4;
                    break;
                case 99:
                    return getValue(0);
                default:
                    return error();
            }
        }
        return error();
    }

    private long getValue(long pos) {
        return memory.get((int) pos);
    }

    private void setValue(long pos, long value) {
        memory.set((int) pos, value);
    }

    private long[] getParameters(int amount) {
        long[] params = new long[amount];
        for (int i = 0; i < amount; i++) {
            params[i] = getValue(pointer + i + 1);
        }
        return params;
    }

    private int error() {
        System.out.println("Something went wrong.");
        return -1;
    }

}
