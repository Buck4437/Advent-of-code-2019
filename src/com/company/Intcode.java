package com.company;

import java.util.ArrayList;
import java.util.Hashtable;

public class Intcode {

    /*
    A little documentation because I can't code:



    To initiate Intcode interpreter, create an Intcode instance and pass through a long array
    as an argument to constructor. The long array is the memory of the interpreter.


    To begin, use run() to run the interpreter.

    The interpreter will return an output based on its condition:
    0: The program has terminated successfully (via opcode 99)
    -1: An error has occurred.
    3: The program is awaiting input (via opcode 3).

    When the interpreter return 3, you can continue the program by running input():
    @param input   This parameter will be used by the program as your input.
                   Accepts long, long[] or ArrayList<Long> for multiple inputs,
                            or String, which will be translated to ASCII-code array.



    You can check the outputs (via opcode 4) of the program by running getOutputs():
    @return ArrayList<Long>      All the outputs of the program, sorted from oldest to newest

    Alternatively, use getOutput() to get a single output:
    @param input     Get the n-th output of the program. Accepts negative number (-1 => last output etc.)



    You can also enable ASCII Mode by running ASCIIMode(), which prints all output as ASCII characters:
    @param state    Enable/Disable ASCII Mode. The default value is false

    getASCIIOutputs() and getASCIIOutput() works similar to getOutputs() and getOutput(). The only difference is that
    the outputs are converted to characters.



    By default, there is a filter that prevents numbers larger than 255 printed as ASCII characters (instead as a number)
    This value can be changed with setASCIIFilter().



    If you want to reset/clear the output of the interpreter, you can run resetOutput().
    This will remove all the outputs from interpreter.



    Logs of the interpreter is off by default.
    You can choose to show the log of the interpreter by using showLog():
    @param showLog   A boolean value that determines whether the log would be shown or hidden



    If you want to reset the program, run reset().
    This will reset its mode, all pointers, output and restore the memory back to its original state.
    */

    long[] initialState;
    ArrayList<Long> inputs = new ArrayList<>();
    ArrayList<Long> output = new ArrayList<>();
    Hashtable<Integer, Long> memory = new Hashtable<>();
    int pointer = 0;
    int relativeBase = 0;
    int ASCIIFilter = 255;
    boolean showLog = false, ASCIIMode = false;

    public Intcode(long[] memory) {
        initialState = memory;
        for (int i = 0; i < memory.length; i++) {
            this.memory.put(i, memory[i]);
        }
    }

    public void reset() {
        this.memory = new Hashtable<>();
        for (int i = 0; i < initialState.length; i++) {
            this.memory.put(i, initialState[i]);
        }
        pointer = 0;
        relativeBase = 0;
        resetOutput();
    }

    public void resetOutput() {
        output = new ArrayList<>();
    }

    public int input(long input) {
        this.inputs.add(input);
        return run();
    }

    public int input(long[] inputs) {
        for (long input : inputs) {
            this.inputs.add(input);
        }
        return run();
    }


    public int input(ArrayList<Long> inputs) {
        this.inputs.addAll(inputs);
        return run();
    }

    public int input(String inputs) {
        ArrayList<Long> codes = new ArrayList<>();
        for (char c : inputs.toCharArray()) {
            codes.add((long) c);
        }
        codes.add((long) '\n');
        return input(codes);
    }

    private boolean hasInput() {
        return this.inputs.size() > 0;
    }

    private long getInput() {
        return this.inputs.remove(0);
    }

    public ArrayList<Long> getOutputs() {
        return output;
    }

    public long getOutput(int pos) {
        return output.get(Math.floorMod(pos, output.size()));
    }

    public void ASCIIMode(boolean state) {
        ASCIIMode = state;
    }

    public ArrayList<Character> getASCIIOutputs() {
        ArrayList<Character> outs = new ArrayList<>();
        for (long num : getOutputs()) {
            outs.add((char) num);
        }
        return outs;
    }

    public char getASCIIOutput(int pos) {
        return getASCIIOutputs().get(Math.floorMod(pos, getASCIIOutputs().size()));
    }

    public void setASCIIFilter(int num) {
        ASCIIFilter = num;
    }

    public void showLog(boolean show) {
        showLog = show;
    }

    public int run() {
        while (true) {
            int instruction = (int) getValue(pointer);
            int opcode = instruction % 100;
            long[] params;
            int[] modes;

//            System.out.println(memory);
//
//            System.out.println("Running opcode " + opcode + " at position " + pointer);

            switch (opcode) {
                case 1:
                    params = getParameters(3);
                    modes = getModes(instruction, 3);

                    setValue(modes[2], params[2], getValue(modes[0], params[0]) + getValue(modes[1], params[1]));

                    pointer += 4;
                    break;
                case 2:
                    params = getParameters(3);
                    modes = getModes(instruction, 3);

                    setValue(modes[2], params[2], getValue(modes[0], params[0]) * getValue(modes[1], params[1]));

                    pointer += 4;
                    break;
                case 3:
                    if (!hasInput()) {
                        log("Awaiting input...");
                        return 3;
                    }

                    long input = getInput();

                    params = getParameters(1);
                    modes = getModes(instruction, 1);

                    log(input + " has been inputted");

                    setValue(modes[0], params[0], input);

                    pointer += 2;
                    break;
                case 4:
                    params = getParameters(1);
                    modes = getModes(instruction, 1);

                    long out = getValue(modes[0], params[0]);
                    output.add(out);
                    log(out + " has been outputted");

                    if (ASCIIMode) {
                        if (out <= ASCIIFilter) {
                            System.out.print((char) out);
                        } else {
                            System.out.print(out);
                        }
                    }

                    pointer += 2;
                    break;
                case 5:
                    params = getParameters(2);
                    modes = getModes(instruction, 2);

                    if (getValue(modes[0], params[0]) != 0) {
                        pointer = (int) getValue(modes[1], params[1]);
                    } else {
                        pointer += 3;
                    }

                    break;
                case 6:
                    params = getParameters(2);
                    modes = getModes(instruction, 2);

                    if (getValue(modes[0], params[0]) == 0) {
                        pointer = (int) getValue(modes[1], params[1]);
                    } else {
                        pointer += 3;
                    }

                    break;
                case 7:
                    params = getParameters(3);
                    modes = getModes(instruction, 3);

                    if (getValue(modes[0], params[0]) < getValue(modes[1], params[1])) {
                        setValue(modes[2], params[2], 1);
                    } else {
                        setValue(modes[2], params[2], 0);
                    }

                    pointer += 4;
                    break;
                case 8:
                    params = getParameters(3);
                    modes = getModes(instruction, 3);

                    if (getValue(modes[0], params[0]) == getValue(modes[1], params[1])) {
                        setValue(modes[2], params[2], 1);
                    } else {
                        setValue(modes[2], params[2], 0);
                    }

                    pointer += 4;
                    break;
                case 9:
                    params = getParameters(1);
                    modes = getModes(instruction, 1);

                    relativeBase += getValue(modes[0], params[0]);

                    pointer += 2;
                    break;
                case 99:
                    log("Number at address 0: " + getValue(0));
                    return 0;
                default:
                    return error("Error: Unknown opcode " + opcode);
            }
        }
    }

    private int[] getModes(long instruction, int parameters) {
        instruction = (instruction - instruction % 100) / 100;
        int[] modes = new int[parameters];
        for (int i = 0; i < parameters; i++) {
            modes[i] = (int) instruction % 10;
            instruction = (instruction - instruction % 10) / 10;
        }
        return modes;
    }

    public long getValue(long parameter) {
        return getValue(0, parameter);
    }

    private long getValue(int mode, long parameter) {
        switch (mode) {
            case 0:
                if (memory.get((int) parameter) == null) {
                    return 0;
                } else {
                    return memory.get((int) parameter);
                }
            case 1:
                return parameter;
            case 2:
                int position = (int) parameter + relativeBase;

                if (memory.get(position) == null) {
                    return 0;
                } else {
                    return memory.get(position);
                }
            default:
                return error("Error: unknown mode " + mode);
        }
    }

    private void setValue(int mode, long parameter, long value) {
        switch (mode) {
            case 0 -> memory.put((int) parameter, value);
            case 1 -> error("Error: an instruction write cannot be in mode 1");
            case 2 -> memory.put((int) parameter + relativeBase, value);
        }
    }

    private long[] getParameters(int amount) {
        long[] params = new long[amount];
        for (int i = 0; i < amount; i++) {
            params[i] = getValue(pointer + i + 1);
        }
        return params;
    }

    private void log(String text) {
        if (showLog) {
            System.out.println(text);
        }
    }

    private int error(String text) {
        System.out.println(text);
        return -1;
    }

}
