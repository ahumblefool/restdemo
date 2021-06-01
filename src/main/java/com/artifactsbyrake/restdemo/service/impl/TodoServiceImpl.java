package com.artifactsbyrake.restdemo.service.impl;

import com.artifactsbyrake.restdemo.service.TodoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class TodoServiceImpl implements TodoService {

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

    @Cacheable(value = "todos", key = "#id")
    @Override
    public String getTodoById(int id) {
        System.out.print("##### FETCHING TODOS FOR ID - " + id + "\n");
        if (id < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID - Please provide a valid ID and retry");

        if (!todos.containsKey(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No todo found with ID");

        return todos.get(id);
    }

    @Cacheable("todos")
    @Override
    public Map getTodosList() {
        System.out.print("##### FETCHING LIST OF TODOS ##### \n");
        return todos;
    }

    @Override
    public Object addTodo(String todo) {
        if (ObjectUtils.isEmpty(todo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty todo");

        todos.put(++seq, todo + " - " + seq);
        URI todoLoc = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(seq)
                .toUri();

        return todoLoc;
    }

    @Override
    public void deleteTodo(int id) {
        if (id < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID - Please provide a valid ID and retry");

        if (!todos.containsKey(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No todo found with ID");

        todos.remove(id);
    }
}
