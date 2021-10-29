package ru.job4j.tracker.action;

/**
 * BaseAction.
 * @author Ivan Belyaev
 * @since 02.10.2017
 * @version 1.0
 */
public abstract class BaseAction implements UserAction {
    /** The name of the menu item. */
    private String name;
    /** The number of the menu item. */
    private int key;

    /**
     * The overridden constructor for objects of heirs.
     * @param name - the name of the menu item.
     * @param key - the number of the menu item.
     */
    public BaseAction(String name, int key) {
        this.name = name;
        this.key = key;
    }

    /**
     * The method displays information about the action.
     * @return returns information about the action.
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key(), this.name);
    }
}
