package ru.job4j.tracker.trackers;

import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackers.ITracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Tracker.
 * @author Ivan Belyaev
 * @since 14.09.2017
 * @version 2.0
 */
public class TrackerInMem implements ITracker {
    /** Field to create a unique identifier for each request. */
    private static final Random RN = new Random();

    /** Array of storage applications. */
    private List<Item> items = new ArrayList<>();

    /** ID counter. */
    private long id = 1;

    /**
     * The method adds a request.
     * @param item - new application.
     * @return returns a reference to the request or null if add failed.
     */
    public Item add(Item item) {
        items.add(item);
        item.setId(id++);
        return item;
    }

    /**
     * The method updates the information in the application.
     * @param item - new information for applications.
     */
    public void update(Item item) {
        long id = item.getId();
        replace(id, item);
    }

    /**
     * The method updates the information in the application.
     * @param id - id of request you want to update
     * @param item - new information for applications.
     */
    public void replace(long id, Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.set(i, item);
                break;
            }
        }
    }

    /**
     * Method removes the application.
     * @param item - the application you want to delete.
     */
    public void delete(Item item) {
        long id = item.getId();
        delete(id);
    }

    /**
     * Method removes the application.
     * @param id - id of request you want to delete
     */
    public void delete(long id) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * The method returns all applications.
     * @return returns an array of all applications.
     */
    public List<Item> findAll() {
        return items;
    }

    /**
     * The method returns all applications with a specific name.
     * @param key - name to search for.
     * @return returns an array of all applications with a specific name.
     */
    public List<Item> findByName(String key) {
        ArrayList<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Method returns the request with a specific ID.
     * @param id - specific ID.
     * @return returns the request with a specific ID.
     */
    public Item findById(long id) {
        Item item = null;
        for (Item elem : items) {
            if (elem.getId() == id) {
                item = elem;
                break;
            }
        }
        return item;
    }

    /**
     * The method generates an ID.
     * @return returns the unique ID.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }
}
