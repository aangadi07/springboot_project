package com.example.todo.controller;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.except.GenreNotFoundException;
import com.example.todo.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST Api
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    //Build addTodo rest API

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto)  //method
    {
        TodoDto savedTodo = todoService.addtodo(todoDto);

        return  new ResponseEntity<>(savedTodo, HttpStatus.CREATED);


    }

    //Build Get Todo REST API
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId)
    {
        TodoDto todoDto = todoService.getTodo(todoId);
        return  new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // Build Get All Todos REST API
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos()
    {
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    // Build update REST API
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoId)
    {
        TodoDto updatedTodo = todoService.updateTodo(todoDto,todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TodoDto>> searchTodos(@RequestParam String query) {
        List<TodoDto> todos = todoService.searchTodos(query);
        return ResponseEntity.ok(todos);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long id) {
        boolean isDeleted = todoService.deleteTodoById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo item not found", HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/search/genre")
    public ResponseEntity<?> searchByGenre(@RequestParam String genre) {
        try {
            List<TodoDto> todos = todoService.searchByGenre(genre);
            return ResponseEntity.ok(todos);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/search/genre/page")
    public ResponseEntity<Page<TodoDto>> searchByGenre(@RequestParam String genre, Pageable pageable)
    {
        Page<TodoDto> todos = todoService.searchByGenre(genre, pageable);
        return ResponseEntity.ok(todos);
    }



}
