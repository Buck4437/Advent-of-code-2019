package com.company.Day06;

import java.util.ArrayList;

public class Obj {

    private final String name;
    private Obj parent = null;
    private final ArrayList<Obj> children = new ArrayList<>();

    public Obj(String name) {
        this.name = name;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void setParent(Obj parent) {
        this.parent = parent;
    }

    public ArrayList<Obj> getParents() {
        ArrayList<Obj> parents = new ArrayList<>();
        if (hasParent()) {
            parents.add(parent);
            parents.addAll(parent.getParents());
        }
        return parents;
    }

    public void addChildren(Obj child) {
        this.children.add(child);
    }

    public int countChildren() {
        int count = 0;
        for (Obj child : children) {
            count += child.countChildren() + 1;
        }
        return count;
    }

    public int getOrbits() {
        int orbits = 0;
        for (Obj child : children) {
            orbits += child.getOrbits() + child.countChildren() + 1;
        }
        return orbits;
    }

    @Override
    public String toString() {
        return "Obj{" +
                "name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
