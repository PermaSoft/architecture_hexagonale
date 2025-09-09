package io.permasoft.archihexa.pretdebd.domain;

public class BookNotFoundExeption extends Throwable {
    public BookNotFoundExeption(String id) {
        super("Book with id " + id + " not found");
    }
}
