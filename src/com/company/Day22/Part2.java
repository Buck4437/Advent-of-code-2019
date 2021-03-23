package com.company.Day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Part2 {

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> instructions = parse();
        LinkedList<Operation> ops = new LinkedList<>();

        for (String s : instructions) {
            ops.addLast(new Operation(s));
        }

        final int SIZE = 10007;
        long time = System.nanoTime();

        boolean hasNewOp = true;
        while (hasNewOp) {
            hasNewOp = false;
            LinkedList<Operation> newOps = new LinkedList<>();
            while (!ops.isEmpty()) {
                Operation op1 = ops.removeFirst();
                if (op1.getOperation() == Operation.CUT && !ops.isEmpty()) {
                    Operation op2 = ops.removeFirst();
                    if (op2.getOperation() == Operation.CUT) {
                        hasNewOp = true;
                        newOps.addLast(new Operation(Operation.CUT, (op1.getArgument() + op2.getArgument()) % SIZE));
                    } else if (op2.getOperation() == Operation.DEAL) {
                        hasNewOp = true;
                        newOps.addLast(new Operation(Operation.DEAL, op2.getArgument()));
                        newOps.addLast(new Operation(Operation.CUT, (op1.getArgument() * op2.getArgument()) % SIZE));
                    } else {
                        newOps.addLast(op1);
                        ops.addFirst(op2);
                    }
                } else if (op1.getOperation() == Operation.DEAL && !ops.isEmpty()) {
                    Operation op2 = ops.removeFirst();
                    if (op2.getOperation() == Operation.DEAL) {
                        hasNewOp = true;
                        newOps.addLast(new Operation(Operation.DEAL, (op1.getArgument() * op2.getArgument()) % SIZE));
                    } else {
                        newOps.addLast(op1);
                        ops.addFirst(op2);
                    }

                } else if (op1.getOperation() == Operation.REVERSE) {
                    hasNewOp = true;
                    newOps.addLast(new Operation(Operation.DEAL, SIZE - 1));
                    newOps.addLast(new Operation(Operation.CUT, 1));
                } else { // is empty
                    newOps.addLast(op1);
                }
            }

            ops = newOps;
        }

        for (Operation op : ops) {
            System.out.println(op);
        }

        ArrayList<String> cards = new ArrayList<>();


        for (int i = 0; i < SIZE; i++) {
            cards.add(Integer.toString(i));
        }

        for (Operation op : ops) {
            int arg = op.getArgument();
            switch (op.getOperation()) {
                case Operation.REVERSE:
                    Collections.reverse(cards);
                    break;
                case Operation.CUT:
                    if (arg > 0) {
                        for (int i = 0; i < arg; i++) {
                            cards.add(cards.remove(0));
                        }
                    } else if (arg < 0) {
                        for (int i = 0; i > arg; i--) {
                            cards.add(0, cards.remove(SIZE - 1));
                        }
                    }
                    break;
                case Operation.DEAL:
                    String[] cards2 = new String[SIZE];
                    int pos = 0;
                    for (String card : cards) {
                        cards2[pos] = card;
                        pos += arg;
                        if (pos >= SIZE) pos -= SIZE;
                    }
                    cards = new ArrayList<>(Arrays.asList(cards2));
                    break;
            }
        }



        System.out.println(cards.indexOf("2019"));
        System.out.println((System.nanoTime() - time)/1e6 + "ms");

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
