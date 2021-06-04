package com.artifactsbyrake.restdemo.controller;

import com.artifactsbyrake.restdemo.service.impl.TodoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoServiceImpl todoService;

    private final String BASE_PATH_V1 = "/api/1/0";

    @Test
    public void testHello() throws Exception {
        mvc.perform(
                get(BASE_PATH_V1 + "/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Rest demo"));
    }

    @Test
    public void testGetUsers() throws Exception {
        //Mock setup
        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "Todo - 1");
        expected.put(2, "Todo - 2");
        Mockito.when(todoService.getTodos()).thenReturn(expected);

        //perform call
        MvcResult result = mvc.perform(
                get(BASE_PATH_V1+ "/todos"))
                .andExpect(status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        Map actual = mapper.readValue(result.getResponse().getContentAsString(), Map.class);

        //Assertions
        Mockito.verify(todoService, Mockito.times(1)).getTodos();
        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(1), actual.get("1"));
    }

}