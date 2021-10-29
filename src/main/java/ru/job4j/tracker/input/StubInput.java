package ru.job4j.tracker.input;

import java.util.ArrayList;

/**
 * StubInput.
 * @author Ivan Belyaev
 * @since 20.09.2017
 * @version 1.0
 */
public class StubInput implements Input {
    /** Pre-prepared answers. */
    private String[] answers;
    /** Counter answers. */
    private int position = 0;

    /**
     * The constructor creates the object StubInput.
     * @param answers - pre-prepared answers.
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }

    /**
     * The method emulates a user survey, giving pre-prepared answers.
     * @param question - a question to the user.
     * @return returns the next pre-prepared response.
     */
    @Override
    public String ask(String question) {
        System.out.print(question);
        System.out.println(this.answers[this.position]);
        return this.answers[this.position++];
    }

    /**
     * The method emulates a user survey, giving pre-prepared answers.
     * @param question - a question to the user.
     * @param range - the range of possible values.
     * @return returns the next pre-prepared response.
     */
    @Override
    public int ask(String question, ArrayList<Integer> range) {
        return Integer.valueOf(this.ask(question));
    }
}
