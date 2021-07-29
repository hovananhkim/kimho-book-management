package com.kimho.book.service;

import java.util.List;

public interface BooksService<T, S> {
    List<T> getAll();

    T get(long id);

    T post(T t);

    T put(S t, long id);

    void delete(long id);
}
