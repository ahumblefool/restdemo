package com.artifactsbyrake.restdemo.controller;

import com.artifactsbyrake.restdemo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/1/0")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Rest demo";
    }

    @GetMapping("/todos")
    public ResponseEntity<Map> getListOfTodos() {
        System.out.println("%%%%%% Finding ALL TODOS");
        return ResponseEntity.ok(todoService.getTodos());
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<String> getTodo(@PathVariable int id) {
        System.out.println("%%%%%% Finding TODO for id - " + id);
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PostMapping("/todos")
    public ResponseEntity<Object> addTodo(@RequestBody String todo) {
        return ResponseEntity.created((URI) todoService.addTodo(todo)).build();
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable int id) {
        return ResponseEntity.noContent().build();
    }
}
