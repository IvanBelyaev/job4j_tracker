package ru.job4j.tracker.input;

import org.junit.Test;
import ru.job4j.tracker.StartUI;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.trackers.ITracker;
import ru.job4j.tracker.trackers.TrackerInMem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * StubInputTest.
 * @author Ivan Belyaev
 * @since 20.09.2017
 * @version 1.0
 */
public class StubInputTest {
    /**
     * Test for the add item.
     */
    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSameName() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        ITracker tracker = new TrackerInMem();
        Item item = new Item("new item", "desc", 1);
        new StartUI(new StubInput(new String[] {"0", item.getName(), item.getDescription(), "1", "6"}), tracker).init();

        assertTrue(
                out.toString().split(System.getProperty("line.separator"))[21].contains(
                        String.format(", name: %s, description: %s, created date: ",
                                item.getName(), item.getDescription()
                        )));
    }

    /**
     * Test for the show all items.
     */
    @Test
    public void whenShowAllItemsThenShowAllItems() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        TrackerInMem tracker = new TrackerInMem();
        Item item = new Item("first", "desc1", 123L);
        tracker.add(item);
        new StartUI(new StubInput(new String[] {"1", "6"}), tracker).init();
        assertThat(
                out.toString().split(System.getProperty("line.separator"))[9],
                is(String.format("id: %s, name: %s, description: %s, created date: %d",
                        item.getId(), item.getName(), item.getDescription(), item.getCreate()))
        );
    }

    /**
     * Test for the edit item.
     */
    @Test
    public void whenUpdateItemThenTrackerHasUpdatedValue() {
        TrackerInMem tracker = new TrackerInMem();
        Item item = new Item("name", "desc", 123L);
        tracker.add(item);
        new StartUI(new StubInput(new String[] {"2", String.valueOf(item.getId()), "new name", "new desc", "6"}), tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("new name"));
    }

    /**
     * Test for the delete item.
     */
    @Test
    public void whenDeleteItemThenTrackerDoesNotHaveItem() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        TrackerInMem tracker = new TrackerInMem();
        Item item1 = new Item("first", "desc1", 123L);
        Item item2 = new Item("second", "desc2", 1234L);
        tracker.add(item1);
        tracker.add(item2);
        new StartUI(
                new StubInput(
                        new String[] {"3", String.valueOf(item1.getId()), "4", String.valueOf(item1.getId()), "6"}
                        ),
                tracker
        ).init();
        assertThat(
                out.toString().split(System.getProperty("line.separator"))[21],
                is("This id does not exist.")
        );
    }

    /**
     * Test for the find by id.
     */
    @Test
    public void whenFindByIdThenTrackerShowsItem() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        TrackerInMem tracker = new TrackerInMem();
        Item item = new Item("first", "desc1", 123L);
        tracker.add(item);
        new StartUI(new StubInput(new String[] {"4", String.valueOf(item.getId()), "2", "6"}), tracker).init();
        assertThat(
                out.toString().split(System.getProperty("line.separator"))[10],
                is(String.format("id: %s, name: %s, description: %s, created date: %d",
                        item.getId(), item.getName(), item.getDescription(), item.getCreate()))
        );
    }

    /**
     * Test for the add new comment.
     */
    @Test
    public void whenUserAddNewCommentToItemThenItemHasComment() {
        TrackerInMem tracker = new TrackerInMem();
        Item item = new Item("name", "desc", 123L);
        tracker.add(item);
        new StartUI(new StubInput(new String[] {"4", String.valueOf(item.getId()), "0", "comment", "2", "6"}), tracker).init();
        assertThat(item.getComments()[0], is("comment"));
    }

    /**
     * Test for the show all comments.
     */
    @Test
    public void whenUserSelectsShowAllCommentsThenShowAllCommentsForItem() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        TrackerInMem tracker = new TrackerInMem();
        Item item = new Item("name", "desc", 123L);
        tracker.add(item);
        new StartUI(
                new StubInput(new String[] {"4", String.valueOf(item.getId()), "0", "comment1", "0", "comment2", "1", "2", "6"}),
                tracker
        ).init();
        assertThat(
                String.format(
                        "%s%s%s",
                        out.toString().split(System.getProperty("line.separator"))[28],
                        System.getProperty("line.separator"),
                        out.toString().split(System.getProperty("line.separator"))[29]
                ),
                is(String.format("comment1%scomment2", System.getProperty("line.separator")))
        );
    }

    /**
     * Test for the find items by name.
     */
    @Test
    public void whenUserFindItemsByNameThenShowAllItemsWithSameName() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        TrackerInMem tracker = new TrackerInMem();
        Item item1 = new Item("name1", "desc1", 123L);
        Item item2 = new Item("name2", "desc2", 1234L);
        Item item3 = new Item("name1", "desc3", 1235L);
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);
        new StartUI(
                new StubInput(new String[] {"5", "name1", "6"}),
                tracker
        ).init();
        assertThat(
                String.format(
                        "%s%s%s",
                        out.toString().split(System.getProperty("line.separator"))[10],
                        System.getProperty("line.separator"),
                        out.toString().split(System.getProperty("line.separator"))[11]
                ),
                is(String.format(
                        "id: %s, name: %s, description: %s, created date: %d%s"
                                + "id: %s, name: %s, description: %s, created date: %d",
                        item1.getId(), item1.getName(), item1.getDescription(), item1.getCreate(),
                        System.getProperty("line.separator"),
                        item3.getId(), item3.getName(), item3.getDescription(), item3.getCreate())
                )
        );
    }
}
