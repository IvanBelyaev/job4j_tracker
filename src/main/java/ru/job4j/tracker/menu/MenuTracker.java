package ru.job4j.tracker.menu;

import ru.job4j.tracker.action.BaseAction;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.action.UserAction;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackers.ITracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for editing applications.
 */
class EditItem extends BaseAction {
    /**
     * The constructor creates the object EditItem.
     * @param name - the name of the menu item.
     * @param key - the number of the menu item.
     */
    EditItem(String name, int key) {
        super(name, key);
    }

    @Override
    public int key() {
        return 2;
    }

    @Override
    public void execute(Input input, ITracker tracker) {
        long id = Long.parseLong(input.ask("Enter id: "));
        Item item = tracker.findById(id);
        if (item == null) {
            System.out.println("This id does not exist.");
        } else {
            System.out.printf("id: %s, name: %s, description: %s, created date: %d\n",
                    item.getId(), item.getName(), item.getDesctiption(), item.getCreate());
            item.setName(input.ask("name: "));
            item.setDesctiption(input.ask("description: "));
            tracker.replace(id, item);
        }
    }
}


/**
 * MenuTracker.
 * @author Ivan Belyaev
 * @since 26.09.2017
 * @version 2.0
 */
public class MenuTracker {
    /** The input / output system. */
    private Input input;
    /** Storage applications. */
    private ITracker tracker;
    /** A repository of all the action. */
    private ArrayList<UserAction> actions = new ArrayList<>();
    /** The range of values menu. */
    private ArrayList<Integer> range = new ArrayList<>();

    /**
     * The constructor creates the object MenuTracker.
     * @param input - the input / output system.
     * @param tracker - storage applications.
     */
    public MenuTracker(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Method fills the vault of the action.
     */
    public void fillActions() {
        this.actions.add(this.new AddItem("Add new Item", 0));
        this.actions.add(new ShowAllItems("Show all items", 1));
        this.actions.add(new EditItem("Edit item", 2));
        this.actions.add(this.new DeleteItem("Delete item", 3));
        this.actions.add(this.new FindItemById("Find item by Id", 4));
        this.actions.add(this.new FindItemByName("Find items by name", 5));
        Collections.addAll(range, 0, 1, 2, 3, 4, 5, 6);
    }

    /**
     * The method returns the range of values menu.
     * @return returns the range of values menu.
     */
    public ArrayList<Integer> getRange() {
        return this.range;
    }

    /**
     * Method displays the main menu.
     */
    public void show() {
        System.out.println();
        for (UserAction action : this.actions) {
            System.out.println(action.info());
        }
        System.out.println("6. Exit Program");
    }

    /**
     * The method performs the selected action.
     * @param key - the number of the menu item.
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Class to add applications.
     */
    private class AddItem extends BaseAction {
        /**
         * The constructor creates the object AddItem.
         * @param name - the name of the menu item.
         * @param key - the number of the menu item.
         */
        AddItem(String name, int key) {
            super(name, key);
        }

        @Override
        public int key() {
            return 0;
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            String name = input.ask("name: ");
            String description = input.ask("description: ");
            tracker.add(new Item(name, description, System.currentTimeMillis()));
            System.out.println("Item added.");
        }
    }

    /**
     * Class to display all applications.
     */
    private static class ShowAllItems extends BaseAction {
        /**
         * The constructor creates the object ShowAllItems.
         * @param name - the name of the menu item.
         * @param key - the number of the menu item.
         */
        ShowAllItems(String name, int key) {
            super(name, key);
        }

        @Override
        public int key() {
            return 1;
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            List<Item> allItems = tracker.findAll();
            for (Item item : allItems) {
                System.out.printf("id: %s, name: %s, description: %s, created date: %d\n",
                        item.getId(), item.getName(), item.getDesctiption(), item.getCreate());
            }
        }
    }

    /**
     * Class to remove the application.
     */
    private class DeleteItem extends BaseAction {
        /**
         * The constructor creates the object DeleteItem.
         * @param name - the name of the menu item.
         * @param key - the number of the menu item.
         */
        DeleteItem(String name, int key) {
            super(name, key);
        }

        @Override
        public int key() {
            return 3;
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            long id = Long.parseLong(input.ask("Enter id: "));
            Item item = tracker.findById(id);
            if (item == null) {
                System.out.println("This id does not exist.");
            } else {
                tracker.delete(item.getId());
                System.out.println("Item deleted.");
            }
        }
    }

    /**
     * Class to search for the application ID.
     */
    private class FindItemById extends BaseAction {
        /**
         * The constructor creates the object FindItemById.
         * @param name - the name of the menu item.
         * @param key - the number of the menu item.
         */
        FindItemById(String name, int key) {
            super(name, key);
        }

        @Override
        public int key() {
            return 4;
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            long id = Long.parseLong(input.ask("Enter id: "));
            Item item = tracker.findById(id);

            if (item == null) {
                System.out.println("This id does not exist.");
            } else {
                System.out.printf("id: %s, name: %s, description: %s, created date: %d\n",
                        item.getId(), item.getName(), item.getDesctiption(), item.getCreate());

                boolean flag = true;
                while (flag) {
                    this.showMenuForItem();
                    String answer = input.ask("Select the menu item: ");
                    switch (answer) {
                        case "0":
                            this.addComment(item);
                            break;
                        case "1":
                            this.showAllComments(item);
                            break;
                        case "2":
                            flag = false;
                            break;
                        default:
                            System.out.println("Error. Try again.");
                    }
                }
            }
        }

        /**
         * The method displays a menu to review the application.
         */
        private void showMenuForItem() {
            System.out.println();
            System.out.println("0. Add new comment");
            System.out.println("1. Show all comments");
            System.out.println("2. Exit menu");
        }

        /**
         * The method adds a request.
         * @param item - application.
         */
        private void addComment(Item item) {
            item.addComment(input.ask("comment: "));
        }

        /**
         * Method displays all applications.
         * @param item - appllication.
         */
        private void showAllComments(Item item) {
            for (String comment : item.getComments()) {
                System.out.println(comment);
            }
        }
    }

    /**
     * Class to search for the application name.
     */
    private class FindItemByName extends BaseAction {
        /**
         * The constructor creates the object FindItemByName.
         * @param name - the name of the menu item.
         * @param key - the number of the menu item.
         */
        FindItemByName(String name, int key) {
            super(name, key);
        }

        @Override
        public int key() {
            return 5;
        }

        @Override
        public void execute(Input input, ITracker tracker) {
            String name = input.ask("name: ");
            List<Item> items = tracker.findByName(name);
            for (Item item : items) {
                System.out.printf("id: %s, name: %s, description: %s, created date: %d\n",
                        item.getId(), item.getName(), item.getDesctiption(), item.getCreate());
            }
        }
    }
}
