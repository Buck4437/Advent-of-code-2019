package com.company.Day22;

import java.util.ArrayList;

public class Node {

    private final int id;
    private Node previous = null;
    private Node next = null;

    public Node(int id) {
        this.id = id;
    }

    public void deal(int i) {
        ArrayList<Node> newOrder = new ArrayList<>();
        getTail().setNext(this); // to form a closed loop
        Node temp = this;
        do {
            temp = temp.getNthNode(i);
            newOrder.add(0, temp);
        } while (temp != this);

        Node temp2 = null;
        for (Node node : newOrder) {
            node.setPrevious(null); // Reset
            node.setNext(null);

            if (temp2 != null) {
                temp2.setNext(node);
                node.setPrevious(temp2);
            }
            temp2 = node;
        }
    }

    public void reverseAll() {
        getHead().reverseAll(true);
    }

    private void reverseAll(boolean a) {
        Node temp = previous;
        previous = next;
        next = temp;

        if (previous != null) {
            previous.reverseAll(true);
        }
    }

    public Node getNode(int i) {
        if (i >= 0) return getHead().getNthNode(i);
        return getTail().getNthNode(i);
    }

    private Node getNthNode(int i) {
        if (i == 0 || i == -1) return this;
        if (i > 0) return getNext().getNthNode(i - 1);
        return getPrevious().getNthNode(i + 1);
    }


    public boolean isHead() {
        return previous == null;
    }

    public boolean isTail() {
        return next == null;
    }

    public Node getHead() {
        if (isHead()) return this;
        return getPrevious().getHead();
    }

    public Node getTail() {
        if (isTail()) return this;
        return getNext().getTail();
    }

    public int getId() {
        return id;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }

    public String allToString() {
        return getHead().allToString(true);
    }

    private String allToString(boolean a) {
        if (isTail()) return toString();
        return toString() + ", " + getNext().allToString(true);
    }
}
