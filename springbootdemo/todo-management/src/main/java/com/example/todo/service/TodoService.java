package com.example.todo.service;

import com.example.todo.dto.TodoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoService {

    //method
    TodoDto addtodo(TodoDto todoDto);



    TodoDto getTodo(Long id);

    List<TodoDto> getAllTodos();

    TodoDto updateTodo(TodoDto todoDto, Long id);

    List<TodoDto> searchTodos(String query);

    boolean deleteTodoById(Long id);

    List<TodoDto> searchByGenre(String genre);

    Page<TodoDto> searchByGenre(String genre, Pageable pageable);




}
