package ru.job4j.tracker.reactive;

public interface Observe<T> {
    void receive(T model);
}
