package com.company.Day23;

public class NAT {

    private Packet packet;

    public NAT() {}

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return new Packet(0, packet.getX(), packet.getY());
    }
}
