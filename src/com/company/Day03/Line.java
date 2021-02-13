package com.company.Day03;

import java.util.ArrayList;
import java.util.Arrays;

public class Line {

    private final int X = 1, Y = 2;
    private final int[] begin, end;
    private final char direction;
    final boolean reversed;
    private final int baseStep;

    public Line(int[] coor1, int[] coor2, int baseStep) {
        if (coor1[0] == coor2[0]) {
            direction = Y;
        } else {
            direction = X;
        }
        if (coor1[1] < coor2[1] || coor1[0] < coor2[0]) {
            reversed = false;
            begin = coor1;
            end = coor2;
        } else {
            reversed = true;
            begin = coor2;
            end = coor1;
        }
        this.baseStep = baseStep;
    }

    public int[] intersection(Line line2) {
        if (direction != line2.getDirection()) {
            int[] begin2 = line2.getBegin();
            int[] end2 = line2.getEnd();
            if (direction == X) {
                if (begin[0] < begin2[0] && begin2[0] < end[0] && begin2[1] < begin[1] && begin[1] < end2[1]) {
                    return new int[]{begin2[0], begin[1]};
                }
            } else {
                if (begin2[0] < begin[0] && begin[0] < end2[0] && begin[1] < begin2[1] && begin2[1] < end[1]) {
                    return new int[]{begin[0], begin2[1]};
                }
            }
        }
        return null;
    }

    public int steps(Line line2) {
        if (direction != line2.getDirection()) {
            int[] begin2 = line2.getBegin();
            int[] end2 = line2.getEnd();
            int steps = 0;
            if (direction == X) {
                if (begin[0] < begin2[0] && begin2[0] < end[0] && begin2[1] < begin[1] && begin[1] < end2[1]) {
                    if (reversed) {
                        steps += (baseStep + end[0] - begin2[0]);
                    } else {
                        steps += (baseStep + begin2[0] - begin[0]);
                    }
                    if (line2.reversed) {
                        steps += (line2.baseStep + end2[1] - begin[1]);
                    } else {
                        steps += (line2.baseStep + begin[1] - begin2[1]);
                    }
                }
            } else {
                if (begin2[0] < begin[0] && begin[0] < end2[0] && begin[1] < begin2[1] && begin2[1] < end[1]) {
                    if (reversed) {
                        steps += (baseStep + end2[0] - begin[0]);
                    } else {
                        steps += (baseStep + begin[0] - begin2[0]);
                    }
                    if (line2.reversed) {
                        steps += (line2.baseStep + end[1] - begin2[1]);
                    } else {
                        steps += (line2.baseStep + begin2[1] - begin[1]);
                    }
                }
            }
            return steps;
        }
        return Integer.MAX_VALUE;
    }

    public char getDirection() {
        return direction;
    }

    public int[] getBegin() {
        return begin;
    }

    public int[] getEnd() {
        return end;
    }
}
