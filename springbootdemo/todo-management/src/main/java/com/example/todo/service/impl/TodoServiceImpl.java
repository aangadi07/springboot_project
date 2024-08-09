package com.example.todo.service.impl;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.except.GenreNotFoundException;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor//creates parameterised constructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository; //dependency of the service class and is injected via constructor

    private ModelMapper modelMapper;

    @Override
    public TodoDto addtodo(TodoDto todoDto) {
        // convert todoDto into  Todo jpa entity

//        Todo todo = new Todo();
//
//        todo.setTitle((todoDto.getTitle())); //sets the title of todo entity using the todoDto
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());

        Todo todo = modelMapper.map(todoDto, Todo.class);


        //todo Jpa entity
        Todo savedTodo = todoRepository.save(todo);

        //Convert saved Todo Jpa enitity object into TOdoDto object
//        TodoDto savedTodoDto = new TodoDto();
//        savedTodoDto.setId(savedTodo.getId());
//        savedTodoDto.setTitle(savedTodo.getTitle());
//        savedTodoDto.setDescription(savedTodo.getDescription());
//        savedTodoDto.setCompleted(savedTodo.isCompleted());

        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);

        return savedTodoDto;




    }


    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));


        return modelMapper.map(todo, TodoDto.class);

    }

    @Override
    public List<TodoDto> getAllTodos() {

        List<Todo> todos = todoRepository.findAll();

        return todos.stream().map((todo)-> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));

       todo.setTitle(todoDto.getTitle());
       todo.setDescription(todoDto.getDescription());
       todo.setCompleted(todoDto.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public List<TodoDto> searchTodos(String query) {
        List<Todo> todos = todoRepository.findByTitleContainingOrDescriptionContaining(query, query);
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTodoById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<TodoDto> searchByGenre(String genre) {
        List<Todo> todos = todoRepository.findByGenreContainingIgnoreCase(genre);
        if (todos.isEmpty()) {
            throw new GenreNotFoundException("Genre is not found in the watchlist");
        }
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<TodoDto> searchByGenre(String genre, Pageable pageable) {
        Page<Todo> todos = todoRepository.findByGenreIgnoreCase(genre, pageable);
        return todos.map(todo -> modelMapper.map(todo, TodoDto.class));
    }

}
