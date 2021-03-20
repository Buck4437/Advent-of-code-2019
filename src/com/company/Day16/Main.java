package com.company.Day16;

public class Main {

    public static void main(String[] args) {
        part2("59776034095811644545367793179989602140948714406234694972894485066523525742503986771912019032922788494900655855458086979764617375580802558963587025784918882219610831940992399201782385674223284411499237619800193879768668210162176394607502218602633153772062973149533650562554942574593878073238232563649673858167635378695190356159796342204759393156294658366279922734213385144895116649768185966866202413314939692174223210484933678866478944104978890019728562001417746656699281992028356004888860103805472866615243544781377748654471750560830099048747570925902575765054898899512303917159138097375338444610809891667094051108359134017128028174230720398965960712");
    }

    public static void part1(String a) {
        String str = a;
        int[] base = {0, 1, 0, -1};

        for (int phase = 0; phase < 100; phase++) {
            String newStr = "";
            for (int i = 0; i < str.length(); i++) {
                int sum = 0;
                for (int j = 0; j < str.length(); j++) {
                    sum += Character.getNumericValue(str.charAt(j)) * (base[(int) ((Math.floor((float) (j+1)/(i+1))) % 4)]);
                }
                newStr += Math.abs(sum) % 10;
            }
            str = newStr;
        }
        System.out.println(str);
    }

    public static void part2(String a) {
        String str = "";

        int offset = Integer.parseInt(a.substring(0, 7));
        for (int i = offset; i < a.length() * 10000; i++) {
            str += a.charAt(i % a.length());
        }


        for (int i = 0; i < 100; i++) {
            System.out.println(i + "/100");
            String newStr = "";
            int sum = 0;
            for (int j = str.length() - 1; j >= 0; j--) {
                sum = (sum + Character.getNumericValue(str.charAt(j))) % 10;
                newStr = sum + newStr;
            }
            str = newStr;
        }
        System.out.println("Decoded: " + str.substring(0, 8));
    }

}
