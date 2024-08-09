package com.example.todo.except;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException(String message)
    {
        super(message);
    }
}
