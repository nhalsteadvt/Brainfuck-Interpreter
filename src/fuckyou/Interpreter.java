/**
 * 
 */
package fuckyou;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author nhalstead
 *
 */
public class Interpreter {
    private File file;

    private int stridx;
    private int pointer;
    private String code;
    private int[] memory;

    /**
     * Creates a Brainfuck interpreter given a filename as a String
     * 
     * @param file
     *            String given used to look for a file to read
     * @throws FileNotFoundException
     *             thrown if no file corresponds with given filename
     */
    public Interpreter(String file) throws FileNotFoundException {
        this.file = new File(file);
        Scanner sc = new Scanner(this.file);

        while (sc.hasNext()) {
            code += sc.nextLine();
        }

        sc.close();

        memory = new int[256];
        stridx = 0;
        pointer = 0;

        // System.out.println(code);
    }


    public Interpreter(String file, int[] mem) throws FileNotFoundException {
        this(file);
        memory = mem;
    }


    /**
     * Runs the program until every character in the code is read or an error
     * occurs
     */
    public void run() {
        while (stridx < code.length()) {
            step();
        }
        System.out.print("\n");
    }


    /**
     * Perform a single step in the program, interpreting one character of code
     */
    public void step() {
        if (!isValChar(code.charAt(stridx))) {
            stridx++;
            return;
        }

        Scanner sc = new Scanner(System.in);

        switch (code.charAt(stridx)) {
            case '>':
                pointer++;
                break;
            case '<':
                pointer--;
                break;
            case '+':
                memory[pointer]++;
                break;
            case '-':
                memory[pointer]--;
                break;
            case '.':
                System.out.print((char)memory[pointer]);
                break;
            case ',':
                memory[pointer] = (int)sc.next().charAt(0);
                break;
            case '[':
                stridx = memory[pointer] == 0
                    ? getMatchingIndex() // code.substring(stridx).indexOf("]")
                    : stridx;
                break;
            case ']':
                stridx = memory[pointer] != 0
                    ? getMatchingIndex() // code.substring(0,
                                         // stridx).lastIndexOf("[")
                    : stridx;
                break;
            default:
                System.out.print("CASE NOT POSSIBLE\n");
        }

        stridx++;

        if (pointer < 0) {
            System.out.println("OUT OF BOUNDS!");
            System.exit(1);
        }

        if (pointer >= memory.length) {
            memory = moreMem(memory);
        }

        if (memory[pointer] < 0) {
            memory[pointer] += 256;
        }

        else if (memory[pointer] > 255) {
            memory[pointer] -= 256;
        }

        sc.close();
    }


    /**
     * Compiles code to get rid of undesirable characters
     */
    public void compile() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            if (isValChar(code.charAt(i))) {
                sb.append(code.charAt(i));
            }
        }
        code = sb.toString();
        assert (validBrackets(code));
    }


    /**
     * Determines if a character is valid
     * 
     * @param a
     *            A single character
     * @return true if it is recognized by brainfuck
     */
    public boolean isValChar(char a) {
        String ans = "><+-.,[]";
        return ans.indexOf(a) != -1;
    }


    private int[] moreMem(int[] mem) {
        int[] ans = new int[mem.length * 2];
        for (int i = 0; i < mem.length; i++) {
            ans[i] = mem[i];
        }
        return ans;
    }


    private int idBracket(char chr) {
        if (chr == '[') {
            return 1;
        }
        else if (chr == ']') {
            return -1;
        }
        return 0;
    }


    private int getMatchingIndex() {
        int val = 0;

        if (code.charAt(stridx) == '[') {
            val++;
            for (int i = stridx + 1; i < code.length(); i++) {
                val += idBracket(code.charAt(i));
                if (val == 0) {
                    return i;
                }
            }
        }
        else if (code.charAt(stridx) == ']') {
            val--;
            for (int i = stridx - 1; i >= 0; i--) {
                val += idBracket(code.charAt(i));
                if (val == 0) {
                    return i;
                }
            }

        }

        return -1;
    }


    private boolean validBrackets(String str) {
        int val = 0;

        for (int i = 0; i < str.length(); i++) {
            val += idBracket(str.charAt(i));
            if (val < 0) {
                return false;
            }
        }

        return val == 0;
    }

}
