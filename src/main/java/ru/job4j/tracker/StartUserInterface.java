package ru.job4j.tracker;

import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.trackers.TrackerSQL;

/**
 * StartUserInterface.
 * @author Ivan Belyaev
 * @since 13.11.2019
 * @version 2.0
 */
public class StartUserInterface {
    /**
     * Application entry point.
     * @param args not used.
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(), new TrackerSQL()).init();
    }
}
