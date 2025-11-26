package com.bookInfo.assignment_01;

import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private static BookManager instance;
    private List<Books> booksList;

    private BookManager() {
        booksList = new ArrayList<>();
    }

    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    public void addBook(Books book) {
        booksList.add(book);
    }

    public List<Books> getAllBooks() {
        return booksList;
    }

    public void clearBooks() {
        booksList.clear();
    }
}