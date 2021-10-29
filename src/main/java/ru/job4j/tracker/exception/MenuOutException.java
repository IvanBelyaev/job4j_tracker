package ru.job4j.tracker.exception;

/**
 * MenuOutException.
 * The exception that is thrown when you select a non-existent menu item.
 * @author Ivan Belyaev
 * @since 18.09.2017
 * @version 1.0
 */
public class MenuOutException extends RuntimeException {
    /**
     * The constructor creates the object MenuOutException.
     * @param message - message.
     */
    public MenuOutException(String message) {
        super(message);
    }
}
