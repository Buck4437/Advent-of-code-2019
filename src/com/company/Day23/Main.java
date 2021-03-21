package com.company.Day23;

import com.company.Intcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.jar.JarEntry;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Computer> computers = new ArrayList<>();
        NAT nat = new NAT();
        long[] instructions = parse();

        for (int i = 0; i < 50; i++) {
            Computer computer = new Computer(instructions);
            computer.setAddress(i);

            computers.add(computer);
        }

        HashSet<Long> prevYs = new HashSet<>();

        while (true) {
            boolean allIdle = true;
            for (int i = 0; i < 50; i++) {
                Computer computer = computers.get(i);
                computer.readPackets();
                ArrayList<Packet> packets = computer.getSentPackets();
                for (Packet packet : packets) {
                    int address = packet.getAddress();
                    if (address == 255) {
                        nat.setPacket(packet);
                    } else {
                        computers.get(address).addPacket(packet);
                    }
                }
                computer.resetSentPackets();
                if (!computer.isIdle()) allIdle = false;
            }

            if (allIdle) {
                Packet packet = nat.getPacket();
                computers.get(0).addPacket(packet);
                if (!prevYs.add(packet.getY())) {
                    System.out.println(packet.getY());
                    break;
                }
            }
        }

    }

    public static long[] parse() throws FileNotFoundException {
        File file = new File("src\\com\\company\\Day23\\input.txt");
        Scanner sc = new Scanner(file);

        String[] numS = sc.nextLine().split(",");
        long[] instructions = new long[numS.length];
        for (int i = 0; i < numS.length; i++) {
            instructions[i] = Long.parseLong(numS[i]);
        }

        return instructions;
    }
}
