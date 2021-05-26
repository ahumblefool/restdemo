package com.artifactsbyrake.restdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/1/0")
public class TodoController {

    private int seq = 1;

    private Map<Integer, String> todos = createDefaultTodos();

    private Map<Integer, String> createDefaultTodos() {
        Map todoMap = new HashMap();
        IntStream
                .range(seq, 5)
                .forEach(i -> {
                    seq = i;
                    String todo = "Todo - " + seq;
                    todoMap.put(seq, todo);
                });
        return todoMap;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Rest demo";
    }

    @GetMapping("/todos")
    public ResponseEntity<Map> getListOfTodos() {
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<String> getTodo(@PathVariable int id) {
        if (id < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID - Please provide a valid ID and retry");

        if (!todos.containsKey(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No todo found with ID");

        return ResponseEntity.ok(todos.get(id));
    }

    @PostMapping("/todos")
    public ResponseEntity<Object> addTodo(@RequestBody String todo) {
        if (ObjectUtils.isEmpty(todo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty todo");

        todos.put(++seq, todo + " - " + seq);
        URI todoLoc = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(seq)
                        .toUri();
        return ResponseEntity.created(todoLoc).build();
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable int id) {
        if (id < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID - Please provide a valid ID and retry");

        if (!todos.containsKey(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No todo found with ID");

        todos.remove(id);

        return ResponseEntity.noContent().build();
    }
}
