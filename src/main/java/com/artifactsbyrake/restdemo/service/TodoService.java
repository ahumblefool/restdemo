package com.artifactsbyrake.restdemo.service;

import java.util.Map;

public interface TodoService {
    String getTodoById(int id);

    Map getTodosList();

    Object addTodo(String todo);

    void deleteTodo(int id);
}
