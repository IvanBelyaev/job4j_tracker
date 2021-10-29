package ru.job4j.tracker.model;

import ru.job4j.tracker.hibernate.LongTimestampConverter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Item.
 * @author Ivan Belyaev
 * @since 14.09.2017
 * @version 1.0
 */
@Entity
@Table(name = "items")
public class Item {
    /** The field contains the counter reviews. */
    @Transient
    private int commentsCounter = 0;
    /** The maximum number of comments. */
    private static final int MAX_COMMENTS = 10;

    /** The name of the application. */
    private String name;
    /** The description of the application. */
    @Column(name = "description")
    private String desctiption;
    /**  Created date. */
    @Column(name = "create_time")
    @Convert(
            converter = LongTimestampConverter.class,
            disableConversion = false)
    private Long create;
    /** Array storage review. */
    @Transient
    private String[] comments = new String[MAX_COMMENTS];
    /** Field contains a unique identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Item creator.
     */
    private String author;

    /**
     * Default constructor.
     */
    public Item() {

    }

    /**
     * The constructor creates the object Item.
     * @param name - the name of the application.
     * @param desctiption - the description of the application.
     * @param create - created date.
     */
    public Item(String name, String desctiption, long create) {
        this.name = name;
        this.desctiption = desctiption;
        this.create = create;
    }

    /**
     * The constructor creates the object Item.
     * @param name - the name of the application.
     * @param desctiption - the description of the application.
     * @param create - created date.
     * @param author item author.
     */
    public Item(String name, String desctiption, long create, String author) {
        this(name, desctiption, create);
        this.author = author;
    }

    /**
     * The method returns the name of the application.
     * @return returns the name of the application.
     */
    public String getName() {
        return this.name;
    }

    /**
     * The method returns the description of the application.
     * @return returns the description of the application.
     */
    public String getDesctiption() {
        return this.desctiption;
    }

    /**
     * The method returns created date of the application.
     * @return returns created date of the application.
     */
    public long getCreate() {
        return this.create;
    }

    /**
     * The method returns array storage review.
     * @return returns array storage review.
     */
    public String[] getComments() {
        String[] result = new String[commentsCounter];
        for (int index = 0; index < commentsCounter; index++) {
            result[index] = comments[index];
        }
        return result;
    }

    /**
     * The method returns the unique identifier.
     * @return returns the unique identifier.
     */
    public long getId() {
        return this.id;
    }

    /**
     * The method sets the new name of the application.
     * @param name - new name of the application.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The method sets the new description of the application.
     * @param desctiption - new description of the application.
     */
    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    /**
     * The method sets the new created date of the application.
     * @param create - new created date of the application.
     */
    public void setCreate(long create) {
        this.create = create;
    }

    /**
     * The method sets the new unique identifier date of the application.
     * @param id - new the unique identifier of the application.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets item author.
     * @return item author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets item author.
     * @param author item author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * The method adds a comment to the ticket.
     * @param newComment - new comment to the ticket.
     */
    public void addComment(String newComment) {
        if (commentsCounter < MAX_COMMENTS) {
            comments[commentsCounter++] = newComment;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id
                && Objects.equals(create, item.create)
                && Objects.equals(name, item.name)
                && Objects.equals(desctiption, item.desctiption)
                && Objects.equals(author, item.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desctiption, create, id, author);
    }

    @Override
    public String toString() {
        return "Item{"
                + "commentsCounter=" + commentsCounter
                + ", name='" + name + '\''
                + ", desctiption='" + desctiption + '\''
                + ", create=" + create
                + ", author=" + author
                + ", comments=" + Arrays.toString(comments)
                + ", id='" + id + '\''
                + '}';
    }
}
