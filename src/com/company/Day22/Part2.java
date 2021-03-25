package com.company.Day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Part2 {

    static final long SIZE = 119315717514047L;

    public static void main(String[] args) throws FileNotFoundException {

        long time = System.nanoTime();

        ArrayList<String> instructions = parse();
        LinkedList<OperationBig> baseOps = new LinkedList<>();
        for (String s : instructions) {
            baseOps.addLast(new OperationBig(s));
        }
        baseOps = compress(baseOps);

        long repetition = 101741582076661L;
        repetition = 119315717514047L - repetition - 1; // Repetitions needed to sort the array

        LinkedList<OperationBig> temp = new LinkedList<>(baseOps);
        LinkedList<OperationBig> ops = new LinkedList<>();
        int count = 0;

        while (true) {
            long bit = (long) Math.pow(2, count);
            if (bit > repetition) break;

            if ((bit & repetition) != 0L) {
                ops.addAll(temp);
                ops = compress(ops);
            }

            LinkedList<OperationBig> temp2 = new LinkedList<>(temp);
            temp2.addAll(temp);
            temp = compress(temp2); // Double the list
            count++;
        }

        for (OperationBig op : ops) {
            System.out.println(op.toString());
        }

        long cardPos = 2020;

        for (OperationBig op : ops) {
            BigInteger arg = op.getArgument();
            switch (op.getOperation()) {
                case OperationBig.REVERSE -> cardPos = SIZE - cardPos - 1;
                case OperationBig.CUT -> cardPos = BigInteger.valueOf(cardPos).subtract(arg).mod(BigInteger.valueOf(SIZE)).longValue();
                case OperationBig.DEAL -> cardPos = BigInteger.valueOf(cardPos).multiply(arg).mod(BigInteger.valueOf(SIZE)).longValue();
            }
        }



        System.out.println(cardPos);
        System.out.println((System.nanoTime() - time)/1e6 + "ms");

    }

    public static LinkedList<OperationBig> compress(LinkedList<OperationBig> input) {

        LinkedList<OperationBig> ops = new LinkedList<>(input);

        boolean hasNewOp = true;
        while (hasNewOp) {
            hasNewOp = false;
            LinkedList<OperationBig> newOps = new LinkedList<>();
            while (!ops.isEmpty()) {
                OperationBig op1 = ops.removeFirst();
                if (op1.getOperation() == OperationBig.CUT && !ops.isEmpty()) {
                    OperationBig op2 = ops.removeFirst();
                    if (op2.getOperation() == OperationBig.CUT) {
                        hasNewOp = true;
                        newOps.addLast(new OperationBig(OperationBig.CUT, (op1.getArgument().add(op2.getArgument())).mod(BigInteger.valueOf(SIZE))));
                    } else if (op2.getOperation() == OperationBig.DEAL) {
                        hasNewOp = true;
                        newOps.addLast(new OperationBig(OperationBig.DEAL, op2.getArgument()));
                        newOps.addLast(new OperationBig(OperationBig.CUT, (op1.getArgument().multiply(op2.getArgument())).mod(BigInteger.valueOf(SIZE))));
                    } else {
                        newOps.addLast(op1);
                        ops.addFirst(op2);
                    }
                } else if (op1.getOperation() == OperationBig.DEAL && !ops.isEmpty()) {
                    OperationBig op2 = ops.removeFirst();
                    if (op2.getOperation() == OperationBig.DEAL) {
                        hasNewOp = true;
                        newOps.addLast(new OperationBig(OperationBig.DEAL, (op1.getArgument().multiply(op2.getArgument())).mod(BigInteger.valueOf(SIZE))));
                    } else {
                        newOps.addLast(op1);
                        ops.addFirst(op2);
                    }

                } else if (op1.getOperation() == OperationBig.REVERSE) {
                    hasNewOp = true;
                    newOps.addLast(new OperationBig(OperationBig.DEAL, SIZE - 1));
                    newOps.addLast(new OperationBig(OperationBig.CUT, 1));
                } else { // is empty
                    newOps.addLast(op1);
                }
            }

            ops = newOps;
        }

        return ops;

    }

    public static ArrayList<String> parse() throws FileNotFoundException {
        File file = new File("src\\com\\company\\Day22\\input.txt");
        Scanner sc = new Scanner(file);

        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        return lines;
    }
}
