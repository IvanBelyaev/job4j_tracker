package ru.job4j.tracker.menu;

import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.menu.MenuTracker;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackers.ITracker;
import ru.job4j.tracker.trackers.TrackerInMem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * MenuTrackerTest.
 *
 * @author Ivan Belyaev
 * @since 13.03.2021
 * @version 1.0
 */
public class MenuTrackerTest {
    /**
     * Test for DeleteItem action when item exists.
     */
    @Test
    public void whenDeleteExistingItemThenItemDeleted() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ITracker tracker = new TrackerInMem();
        Item item = new Item("name", "desc", 1);
        tracker.add(item);
        long id = item.getId();
        Input input = mock(Input.class);
        when(input.ask(any(String.class))).thenReturn(String.valueOf(id));

        MenuTracker menuTracker = new MenuTracker(input, tracker);
        menuTracker.fillActions();
        menuTracker.select(3);

        assertThat(out.toString().split("\n")[0], is("Item deleted."));
        assertNull(tracker.findById(id));
    }

    /**
     * Test for DeleteItem action when item doesn't exist.
     */
    @Test
    public void whenDeleteNotExistingItemThenMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ITracker tracker = new TrackerInMem();
        Input input = mock(Input.class);
        when(input.ask(any(String.class))).thenReturn("1");

        MenuTracker menuTracker = new MenuTracker(input, tracker);
        menuTracker.fillActions();
        menuTracker.select(3);

        assertThat(out.toString().split("\n")[0], is("This id does not exist."));
    }

    /**
     * Test for FindItemByName action.
     */
    @Test
    public void whenFindItemByNameThenShowsItems() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ITracker tracker = new TrackerInMem();
        Item item1 = new Item("name", "desc1", 1);
        Item item2 = new Item("name", "desc2", 1);
        tracker.add(item1);
        tracker.add(item2);
        Input input = mock(Input.class);
        when(input.ask(any(String.class))).thenReturn("name");

        MenuTracker menuTracker = new MenuTracker(input, tracker);
        menuTracker.fillActions();
        menuTracker.select(5);

        assertThat(
                out.toString().split("\n")[0],
                is(String.format("id: %s, name: %s, description: %s, created date: %d",
                        item1.getId(), item1.getName(), item1.getDesctiption(), item1.getCreate()
                ))
        );
        assertThat(
                out.toString().split("\n")[1],
                is(String.format("id: %s, name: %s, description: %s, created date: %d",
                        item2.getId(), item2.getName(), item2.getDesctiption(), item2.getCreate()
                ))
        );
    }

    /**
     * Test for FindItemById action when id doesn't exist.
     */
    @Test
    public void whenFindItemByIdAndIdDoesNotExistThenMessage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ITracker tracker = new TrackerInMem();
        Input input = mock(Input.class);
        when(input.ask(any(String.class))).thenReturn("1");

        MenuTracker menuTracker = new MenuTracker(input, tracker);
        menuTracker.fillActions();
        menuTracker.select(4);

        assertThat(out.toString().split("\n")[0], is("This id does not exist."));
    }

    /**
     * Test for FindItemById action when id exists.
     */
    @Test public void whenFindItemByIdAndIdExistsThenShowMenu() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ITracker tracker = new TrackerInMem();
        Item item = new Item("name", "desc", 1);
        tracker.add(item);
        long id = item.getId();
        Input input = mock(Input.class);
        when(input.ask(any(String.class))).thenReturn(String.valueOf(id)).thenReturn("2");

        MenuTracker menuTracker = new MenuTracker(input, tracker);
        menuTracker.fillActions();
        menuTracker.select(4);

        assertThat(
                out.toString().split("\n")[0],
                is(String.format("id: %s, name: %s, description: %s, created date: %d",
                        item.getId(), item.getName(), item.getDesctiption(), item.getCreate())
                )
        );
    }
}
