package ru.job4j.tracker.input;

import java.util.ArrayList;

/**
 * Input.
 * @author Ivan Belyaev
 * @since 18.09.2017
 * @version 1.0
 */
public interface Input {
    /**
     * The method asks the user.
     * @param question - a question to the user.
     * @return returns the user response.
     */
    String ask(String question);

    /**
     * Method requests input from the user and checks them for validity.
     * @param question - a question to the user.
     * @param range - the range of possible values.
     * @return returns the user response.
     */
    int ask(String question, ArrayList<Integer> range);
}