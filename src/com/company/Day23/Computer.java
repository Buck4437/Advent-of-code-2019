package com.company.Day23;

import com.company.Intcode;

import java.util.ArrayList;

public class Computer {

    private final Intcode intcode;
    private int address;
    private ArrayList<Packet> queue = new ArrayList<>();
    private boolean idle = false;

    public Computer(long[] instructions) {
        this.intcode = new Intcode(instructions);
    }

    public void setAddress(int address) {
        this.address = address;
        intcode.input(address);
    }

    public ArrayList<Packet> getSentPackets() {

        ArrayList<Packet> packets = new ArrayList<>();
        for (int i = 0; i < intcode.getOutputs().size(); i += 3) {

            int address = (int) intcode.getOutput(i);
            long x = intcode.getOutput(i + 1);
            long y = intcode.getOutput(i + 2);
            packets.add(new Packet(address, x, y));

        }

        return packets;
    }

    public void resetSentPackets() {
        intcode.resetOutput();
    }

    public void addPacket(Packet packet) {
        queue.add(packet);
    }

    public void readPackets() {
        idle = false;
        if (queue.isEmpty()) {
            int length = intcode.getOutputs().size();

            intcode.input(-1);

            if (length == intcode.getOutputs().size()) { // No output
                idle = true;
            }
        } else {
            for (Packet packet : queue) {
                if (packet.getAddress() != address) {
                    System.out.printf("Error: A packet with address %s was wrongly sent to computer with address %s\n"
                            , packet.getAddress(), address);
                } else {
                    intcode.input(new long[]{packet.getX(), packet.getY()});
                }
            }
        }
        resetQueue();
    }

    public void resetQueue() {
        queue = new ArrayList<>();
    }

    public boolean isIdle() {
        return idle;
    }
}
