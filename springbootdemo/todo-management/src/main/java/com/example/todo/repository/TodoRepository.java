package com.example.todo.repository;

import com.example.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByTitleContainingOrDescriptionContaining(String title, String description);

    List<Todo> findByGenreContainingIgnoreCase(String genre);

    Page<Todo> findByGenreIgnoreCase(String genre, Pageable pageable);




}
