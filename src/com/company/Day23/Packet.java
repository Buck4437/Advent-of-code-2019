package com.company.Day23;

public class Packet {

    private final long x;
    private final long y;
    private final int address;

    public Packet(int address, long x, long y) {
        this.address = address;
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public int getAddress() {
        return address;
    }
}
