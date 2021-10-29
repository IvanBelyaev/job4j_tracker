package ru.job4j.tracker;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.menu.MenuTracker;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackers.ITracker;
import ru.job4j.tracker.trackers.TrackerInMem;

import java.util.List;

/**
 * StarUI.
 * Entry point.
 * @author Ivan Belyaev
 * @since 12.09.2017
 * @version 1.0
 */
public class StartUI {
    /** Storage applications. */
    private ITracker tracker;
    /** Object input / output. */
    private Input input;

    /**
     * The constructor creates the object StarUI.
     * @param input - interface input / output.
     * @param tracker - storage applications.
     */
    public StartUI(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * The method executes the main program loop.
     */
    public void init() {
        MenuTracker menuTracker = new MenuTracker(input, tracker);
        menuTracker.fillActions();
        boolean flag = true;
        do {
            menuTracker.show();
            int key = input.ask("Select the menu item: ", menuTracker.getRange());
            if (key == 6) {
                flag = false;
            } else {
                menuTracker.select(key);
            }
        } while (flag);
    }

    /**
     * The application entry point.
     * @param args - the argument is not being used.
     */
    public static void main(String[] args) {
        new StartUI(new ValidateInput(), new TrackerInMem()).init();
    }

    /**
     * Method displays the main menu.
     */
    private void showMenu() {
        System.out.println();
        System.out.println("0. Add new Item");
        System.out.println("1. Show all items");
        System.out.println("2. Edit item");
        System.out.println("3. Delete item");
        System.out.println("4. Find item by Id");
        System.out.println("5. Find items by name");
        System.out.println("6. Exit Program");
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
     */
    private void addItem() {
        String name = this.input.ask("name: ");
        String description = this.input.ask("description: ");
        this.tracker.add(new Item(name, description, System.currentTimeMillis()));
        System.out.println("Item added.");
    }

    /**
     * Method displays all applications.
     */
    private void showAllItem() {
        this.tracker.findAll(item ->
                System.out.printf("id: %s, name: %s, description: %s, created date: %d\n",
                        item.getId(), item.getName(), item.getDescription(), item.getCreate()));
    }

    /**
     * The method edits the application.
     */
    private void editItem() {
        long id = Long.parseLong(this.input.ask("Enter id: "));
        Item item = this.tracker.findById(id);
        if (item == null) {
            System.out.println("This id does not exist.");
        } else {
            System.out.printf("id: %s, name: %s, description: %s, created date: %d\n",
                    item.getId(), item.getName(), item.getDescription(), item.getCreate());
            item.setName(this.input.ask("name: "));
            item.setDescription(this.input.ask("description: "));
        }
    }

    /**
     * Method removes the application.
     */
    private void deleteItem() {
        long id = Long.parseLong(this.input.ask("Enter id: "));
        Item item = this.tracker.findById(id);
        if (item == null) {
            System.out.println("This id does not exist.");
        } else {
            this.tracker.delete(item.getId());
            System.out.println("Item deleted.");
        }
    }

    /**
     * The method searches the application by a unique identifier.
     * Method allows you to view and add comments to the application.
     */
    private void findItemById() {
        long id = Long.parseLong(this.input.ask("Enter id: "));
        Item item = this.tracker.findById(id);

        if (item == null) {
            System.out.println("This id does not exist.");
        } else {
            System.out.printf("id: %s, name: %s, description: %s, created date: %d\n",
                    item.getId(), item.getName(), item.getDescription(), item.getCreate());

            boolean flag = true;
            while (flag) {
                this.showMenuForItem();
                String answer = this.input.ask("Select the menu item: ");
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
     * Method displays all applications with a specific name.
     */
    private void findItemByName() {
        String name = this.input.ask("name: ");
        List<Item> items = this.tracker.findByName(name);
        for (Item item : items) {
            System.out.printf("id: %s, name: %s, description: %s, created date: %d\n",
                    item.getId(), item.getName(), item.getDescription(), item.getCreate());
        }
    }

    /**
     * The method adds a comment to the ticket.
     * @param item - application.
     */
    private void addComment(Item item) {
        item.addComment(this.input.ask("comment: "));
    }

    /**
     * Method shows all comments of this application.
     * @param item - application.
     */
    private void showAllComments(Item item) {
        for (String comment : item.getComments()) {
            System.out.println(comment);
        }
    }
}
