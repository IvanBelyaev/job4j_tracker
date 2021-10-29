package ru.job4j.tracker.input;

import ru.job4j.tracker.exception.MenuOutException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * ConsoleInput.
 * @author Ivan Belyaev
 * @since 18.09.2017
 * @version 1.0
 */
public class ConsoleInput implements Input {
    /** Object for reading from the console data entered by the user. */
    private Scanner scanner = new Scanner(System.in);

    /**
     * The method asks the user via the console.
     * @param question - a question to the user.
     * @return returns the user response.
     */
    @Override
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    /**
     * Method requests input from the user and checks them for validity.
     * @param question - a question to the user.
     * @param range - the range of possible values.
     * @return returns the user response.
     */
    @Override
    public int ask(String question, ArrayList<Integer> range) {
        int answer = Integer.valueOf(this.ask(question));
        boolean contain = false;

        for (int key : range) {
            if (key == answer) {
                contain = true;
                break;
            }
        }

        if (contain) {
            return answer;
        } else {
            throw new MenuOutException("Out of menu range.");
        }
    }
}
