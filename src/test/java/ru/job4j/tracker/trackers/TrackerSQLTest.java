package ru.job4j.tracker.trackers;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.tracker.ConnectionRollback;
import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * TrackerSQLTest.
 * @author Ivan Belyaev
 * @since 13.11.2019
 * @version 1.0
 */
public class TrackerSQLTest {
    /**
     * Connection initialization.
     * @return database connection.
     */
    public static Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @BeforeClass
    public static void createTable() throws SQLException {
        Connection connection = init();
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS items ("
                    + "id SERIAL PRIMARY KEY,"
                    + "name VARCHAR(50) NOT NULL,"
                    + "description VARCHAR(350) NOT NULL,"
                    + "create_time TIMESTAMP NOT NULL)";
            statement.executeUpdate(sql);
            sql = "DELETE FROM items";
            statement.executeUpdate(sql);
        }
    }

    /**
     * Test for the add method.
     * @throws SQLException possible exception.
     */
    @Test
    public void createItem() throws SQLException {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name", "desc", System.currentTimeMillis()));
            assertThat(tracker.findByName("name").size(), is(1));
        }
    }

    /**
     * Test for the update method.
     * @throws SQLException possible exception.
     */
    @Test
    public void whenUpdateNameThenReturnNewName() throws SQLException {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1", "desc1", 123L);
            tracker.add(item1);
            Item item2 = new Item("name2", "desc2", 1234L);
            item2.setId(item1.getId());
            tracker.replace(item2.getId(), item2);
            String expected = "name2";
            assertThat(tracker.findById(item1.getId()).getName(), is(expected));
        }
    }

    /**
     * Test for the delete method.
     * @throws SQLException possible exception.
     */
    @Test
    public void whenDeleteOneFromTwoThenOne() throws SQLException {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1", "desc1", 123L);
            tracker.add(item1);
            Item item2 = new Item("name2", "desc2", 1234L);
            tracker.add(item2);
            tracker.delete(item1.getId());
            List<Item> methodReturns = tracker.findAll();

            ArrayList<Item> expected = new ArrayList<>();
            expected.add(item2);

            assertThat(methodReturns, is(expected));
        }
    }

    /**
     * Test for the findAll method.
     * @throws SQLException possible exception.
     */
    @Test
    public void whenFindAllTwoFromTwoThenTwo() throws SQLException {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1", "desc1", 123L);
            tracker.add(item1);
            Item item2 = new Item("name2", "desc2", 1234L);
            tracker.add(item2);
            List<Item> methodReturns = tracker.findAll();

            ArrayList<Item> expected = new ArrayList<>();
            expected.add(item1);
            expected.add(item2);

            assertThat(methodReturns, is(expected));
        }
    }

    /**
     * Test for the findByName method.
     * @throws SQLException possible exception.
     */
    @Test
    public void whenFindByNameAsTaskThenTwo() throws SQLException {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("task", "desc1", 123L);
            tracker.add(item1);
            Item item2 = new Item("name", "desc2", 1234L);
            tracker.add(item2);
            Item item3 = new Item("task", "desc4", 12345L);
            tracker.add(item3);
            List<Item> methodReturns = tracker.findByName("task");

            ArrayList<Item> expected = new ArrayList<>();
            expected.add(item1);
            expected.add(item3);

            assertThat(methodReturns, is(expected));
        }
    }

    /**
     * Test for the findById method.
     * @throws SQLException possible exception.
     */
    @Test
    public void whenFindByIdThenItemWithUniqueId() throws SQLException {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("task", "desc1", 123L);
            tracker.add(item1);
            Item item2 = new Item("name", "desc2", 1234L);
            tracker.add(item2);
            Item item3 = new Item("task", "desc4", 12345L);
            tracker.add(item3);
            Item methodReturns = tracker.findById(item2.getId());
            Item expected = item2;
            assertThat(methodReturns, is(expected));
        }
    }
}