package ru.job4j.tracker.input;

import ru.job4j.tracker.exception.MenuOutException;

import java.util.ArrayList;

/**
 * ValidateInput.
 * @author Ivan Belyaev
 * @since 27.09.2017
 * @version 1.0
 */
public class ValidateInput extends ConsoleInput {
    /**
     * Method requests input from the user and checks them for validity.
     * @param question - a question to the user.
     * @param range - the range of possible values.
     * @return returns the user response.
     */
    @Override
    public int ask(String question, ArrayList<Integer> range) {
        int key = -1;
        boolean invalid = true;
        do {
            try {
                key = super.ask(question, range);
                invalid = false;
            } catch (NumberFormatException nfe) {
                System.out.println("Error. Please enter a number.");
            } catch (MenuOutException moe) {
                System.out.println("Error. Please select the key from the menu.");
            }
        } while (invalid);
        return key;
    }
}
