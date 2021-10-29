package ru.job4j.tracker.trackers;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.reactive.Observe;

import java.util.List;

/**
 * TrackerHbm.
 */
public class TrackerHbm implements ITracker, AutoCloseable {
    /** A registry of services. */
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    /** Session factory. */
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public void replace(long id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        item.setId(id);
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(long id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void findAll(Observe<Item> observe) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("from Item", Item.class).getResultStream()
                .forEach(item -> observe.receive(item));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Item> findByName(String name) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Item I where I.name = :name");
        query.setParameter("name", name);
        List<Item> items = query.list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public Item findById(long id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public void close() {
        sf.close();
        registry.close();
    }
}