package com.artifactsbyrake.restdemo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.artifactsbyrake.restdemo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @Test
    public void testSayHello() throws Exception {
        mockMvc.perform(get("/api/1/0/hello"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello Rest demo"));
    }

    @Test
    public void testGetListOfTodos() throws Exception {
        mockMvc.perform(get("/api/1/0/todos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.yourExpectedResponseField").value("yourExpectedValue"));
        // Add more assertions based on your expected response
    }

    @Test
    public void testGetTodoById() throws Exception {
        int todoId = 1; // Replace with a valid todo id
        mockMvc.perform(get("/api/1/0/todos/{id}", todoId))
               .andExpect(status().isOk())
               .andExpect(content().string("yourExpectedTodoResponse"));
        // Add more assertions based on your expected response
    }

    @Test
    public void testAddTodo() throws Exception {
        String newTodo = "yourNewTodo";
        ResultActions resultActions = mockMvc.perform(post("/api/1/0/todos")
               .content(newTodo)
               .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                      .andExpect(header().exists("Location"));
        // Add more assertions based on your expected response
    }

    @Test
    public void testDeleteTodo() throws Exception {
        int todoId = 1; // Replace with a valid todo id
        mockMvc.perform(delete("/api/1/0/todos/{id}", todoId))
               .andExpect(status().isNoContent());
        // Add more assertions based on your expected response
    }
}
