/**
 * 
 */
package fuckyou;

import java.io.FileNotFoundException;

/**
 * @author nhalstead
 *
 */
public class Runner {
    public static void main(String[] args) throws FileNotFoundException {
        Interpreter code1 = new Interpreter("alpha_brainfuck.txt");
        Interpreter code2 = new Interpreter("alpha_space_brainfuck.txt");
        Interpreter code3 = new Interpreter("Greeting_brainfuck.txt");
        Interpreter code4 = new Interpreter("number_alpha_brainfuck.txt");

        // int[] test = new int[] { 10, 2, 9, 48, 49, 32, 65, 0, 0, 0, 0 };
        // Interpreter code5 = new Interpreter("testing.txt", test);

        code1.compile();
        code1.run();

        code2.compile();
        code2.run();

        code3.compile();
        code3.run();

        code4.compile();
        code4.run();

        // code5.compile();
        // code5.run();
    }
}
