package com.example.week3;

import com.example.week3.service.DemoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {Week3Application.class})
@AutoConfigureMockMvc
class Week3ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoService demoService;

    @Test
    @DisplayName("Test Hello World Endpoint")
    void testHelloWorldEndpoint() throws Exception {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello World"));
    }

    @Test
    @DisplayName("Test the endpoint for adding a name to the list")
    void testAddNameEndpoint() throws Exception {
        mockMvc.perform(get("/add")
            .param("name", "Dan"))
            .andExpect(status().isOk());
        assertTrue(demoService.getNames().contains("Dan"));
    }

    @Test
    @DisplayName("Test the endpoint for removing a name from the list")
    void testRemoveNameEndpoint() throws Exception {
        demoService.add("Dan");
        mockMvc.perform(get("/remove")
                .param("name", "Dan"))
                .andExpect(status().isOk());
        assertFalse(demoService.getNames().contains("Dan"));
    }

    @Test
    @DisplayName("Test the endpoint for getting the list of names")
    void testListNamesEndpoint() throws Exception {
        demoService.add("Dan");
        demoService.add("Dan2");
        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
            .andExpect(content().json("[\"Dan\", \"Dan2\"]"));
        assertTrue(demoService.getNames().contains("Dan"));
    }

    @Test
    @DisplayName("Test the endpoint for viewing a name on the list")
    void testViewNameEndpoint() throws Exception {
        demoService.add("Dan");
        mockMvc.perform(get("/view/{name}", "Dan"))
                .andExpect(status().isOk())
            .andExpect(content().string("Dan"));
    }

}
