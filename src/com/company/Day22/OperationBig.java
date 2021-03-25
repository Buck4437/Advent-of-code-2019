package com.company.Day22;

import java.math.BigInteger;

public class OperationBig {

    public static final int UNDEFINED = -1;
    public static final int REVERSE = 1;
    public static final int CUT = 4;
    public static final int DEAL = 7;

    private int operation = UNDEFINED;
    private BigInteger argument = new BigInteger(String.valueOf(UNDEFINED));

    public OperationBig(String s) {
        if (s.contains("deal into new stack")) {
            operation = REVERSE;
        } else if (s.contains("cut")) {
            operation = CUT;
            setArgument(new BigInteger(s.split(" ")[1]));
        } else if (s.contains("deal with increment")) {
            operation = DEAL;
            setArgument(new BigInteger(s.split(" ")[3]));
        }
    }

    public OperationBig(int operation) {
        this(operation, new BigInteger(String.valueOf(UNDEFINED)));
    }

    public OperationBig(int operation, int argument) {
        this(operation, BigInteger.valueOf(argument));
    }

    public OperationBig(int operation, long argument) {
        this(operation, BigInteger.valueOf(argument));
    }

    public OperationBig(int operation, BigInteger argument) {
        this.operation = operation;
        this.argument = argument;
    }

    public int getOperation() {
        return operation;
    }

    private void setArgument(BigInteger i) {
        this.argument = i;
    }

    public BigInteger getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operation=" + operation +
                ", argument=" + argument +
                '}';
    }
}
