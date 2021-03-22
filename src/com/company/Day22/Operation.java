package com.company.Day22;

public class Operation {

    public static final int UNDEFINED = -1;
    public static final int REVERSE = 1;
    public static final int CUT = 4;
    public static final int DEAL = 7;

    private int operation = UNDEFINED;
    private int argument = UNDEFINED;

    public Operation(String s) {
        if (s.contains("deal into new stack")) {
            operation = REVERSE;
        } else if (s.contains("cut")) {
            operation = CUT;
            setArgument(Integer.parseInt(s.split(" ")[1]));
        } else if (s.contains("deal with increment")) {
            operation = DEAL;
            setArgument(Integer.parseInt(s.split(" ")[3]));
        }
    }

    public int getOperation() {
        return operation;
    }

    private void setArgument(int i) {
        this.argument = i;
    }

    public int getArgument() {
        return argument;
    }
}
