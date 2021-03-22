package com.company.Day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<String> instructions = parse();
        LinkedList<Operation> ops = new LinkedList<>();

        Hashtable<Integer, Node> cards = new Hashtable<>();

        for (String s : instructions) {
            ops.addLast(new Operation(s));
        }

        for (int i = 0; i < 10; i++) {
            Node node = new Node(i);
            Node previous = cards.get(i - 1);
            if (previous != null) {
                node.setPrevious(previous);
                previous.setNext(node);
            }
            cards.put(i, node);
        }

        for (Operation op : ops) {
            switch (op.getOperation()) {
                case Operation.REVERSE:
                    cards.get(0).reverseAll();
                    break;
                case Operation.CUT:
                    Node start = cards.get(0).getHead();
                    Node end = cards.get(0).getTail();
                    Node secondStart = start.getNode(op.getArgument());
                    end.setNext(start);
                    start.setPrevious(end);
                    secondStart.getPrevious().setNext(null);
                    secondStart.setPrevious(null);
                    break;
                case Operation.DEAL:
                    cards.get(0).getHead().deal(op.getArgument());
                    break;
            }
        }

        System.out.println(cards.get(0).allToString());

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
