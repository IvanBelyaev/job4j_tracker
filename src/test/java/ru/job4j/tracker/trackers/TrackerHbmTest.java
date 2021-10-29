package ru.job4j.tracker.trackers;

import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * TrackerHbmTest.
 */
public class TrackerHbmTest {
    /**
     * Test for add() method.
     */
    @Test
    public void whenAddItemThenDBHasTheItem() {
        TrackerHbm trackerHbm = new TrackerHbm();
        Item item = new Item("first task", "desc", 1L, "sergey");

        trackerHbm.add(item);

        List<Item> allItems = trackerHbm.findAll();
        assertEquals(1, allItems.size());
        assertEquals(item, allItems.get(0));
    }

    /**
     * Test for replace() method.
     */
    @Test
    public void whenReplaceItemThenDBHasNewItemWithTheSameId() {
        TrackerHbm trackerHbm = new TrackerHbm();
        Item first = new Item("first task", "desc", 1L, "sergey");
        Item second = new Item("second task", "desc2", 2L, "oleg");
        trackerHbm.add(first);
        trackerHbm.add(second);

        Item newItem = new Item("updated task", "desc3", 3L, "igor");
        trackerHbm.replace(first.getId(), newItem);

        Item updatedItem = trackerHbm.findById(first.getId());
        Item notUpdatedItem = trackerHbm.findById(second.getId());
        assertEquals(newItem, updatedItem);
        assertEquals(second, notUpdatedItem);
    }

    /**
     * Test for delete() method.
     */
    @Test
    public void whenDeleteItemThenItemWasDeletedFromDB() {
        TrackerHbm trackerHbm = new TrackerHbm();
        Item first = new Item("first task", "desc", 1L, "sergey");
        Item second = new Item("second task", "desc2", 2L, "oleg");
        trackerHbm.add(first);
        trackerHbm.add(second);

        trackerHbm.delete(first.getId());

        List<Item> allItems = trackerHbm.findAll();
        assertEquals(1, allItems.size());
        assertEquals(second, allItems.get(0));
    }

    /**
     * Test for findAll() method.
     */
    @Test
    public void whenFindAllItemsThenReturnsListOfAllItemsFromDB() {
        TrackerHbm trackerHbm = new TrackerHbm();
        Item first = new Item("first task", "desc", 1L, "sergey");
        Item second = new Item("second task", "desc2", 2L, "oleg");
        trackerHbm.add(first);
        trackerHbm.add(second);

        List<Item> allItems = trackerHbm.findAll();

        assertEquals(2, allItems.size());
        assertTrue(allItems.contains(first));
        assertTrue(allItems.contains(second));
    }

    /**
     * Test for findByName() method.
     */
    @Test
    public void whenFindItemsByNameThenReturnsListOfAllItemsWithTheSameName() {
        TrackerHbm trackerHbm = new TrackerHbm();
        Item first = new Item("first task", "desc", 1L, "sergey");
        Item second = new Item("second task", "desc2", 2L, "oleg");
        Item third = new Item("second task", "desc3", 3L, "igor");
        trackerHbm.add(first);
        trackerHbm.add(second);
        trackerHbm.add(third);

        List<Item> allItemsWithSpecificName = trackerHbm.findByName("second task");

        assertEquals(2, allItemsWithSpecificName.size());
        assertTrue(allItemsWithSpecificName.contains(second));
        assertTrue(allItemsWithSpecificName.contains(third));
    }

    /**
     * Test for findById() method.
     */
    @Test
    public void whenFindItemByIdThenReturnItemWithTheSameId() {
        TrackerHbm trackerHbm = new TrackerHbm();
        Item first = new Item("first task", "desc", 1L, "sergey");
        Item second = new Item("second task", "desc2", 2L, "oleg");
        trackerHbm.add(first);
        trackerHbm.add(second);

        Item itemWithId = trackerHbm.findById(first.getId());

        assertEquals(first, itemWithId);
    }
}
