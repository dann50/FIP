package com.example.week7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {Week7Application.class})
@AutoConfigureMockMvc
class Week7ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test register endpoint with proper credentials" +
        " and ensure that registration is successful")
    void testRegisterEndpointHappyFlow() throws Exception {
        mockMvc.perform(post("/register")
            .contentType("application/json")
            .content("""
                {"firstName": "Dan", "lastName": "Josh", "email": "dan@example.com", "password": "Dan123"}
                """))
            .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Test register endpoint with bad credentials and" +
        " ensure that registration is unsuccessful")
    void testRegisterEndpointBadCredentialsFlow() throws Exception {
        // put a wrong password and email format
        mockMvc.perform(post("/register")
                .contentType("application/json")
                .content("""
                {"firstName": "Dan", "lastName": "Josh", "email": "danexample.com", "password": "Dan12"}
                """))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test register endpoint with credentials that already exist and" +
        " ensure that registration is unsuccessful")
    void testRegisterEndpointAlreadyRegisteredFlow() throws Exception {
        testRegisterEndpointHappyFlow();
        mockMvc.perform(post("/register")
                .contentType("application/json")
                .content("""
                {"firstName": "Dan", "lastName": "David", "email": "dan@example.com", "password": "Dan123"}
                """))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test login endpoint and ensure that login is successful")
    void testLoginEndpoint() throws Exception {
        testRegisterEndpointHappyFlow();
        mockMvc.perform(post("/login")
                    .param("email", "dan@example.com")
                    .param("password", "Dan123")
                )
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test login endpoint with bad credentials and" +
        " ensure that login is unsuccessful")
    void testLoginEndpointExceptionFlow() throws Exception {
        testRegisterEndpointHappyFlow();
        mockMvc.perform(post("/login")
                .param("email", "dan@example.com")
                .param("password", "Dan12345")
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test the endpoint for viewing users")
    void testViewUsers() throws Exception {
        testRegisterEndpointHappyFlow();
        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("Test endpoint for deleting user and ensure " +
        "that deletion is successful")
    void testDeleteUser() throws Exception {
        testRegisterEndpointHappyFlow();
        mockMvc.perform(delete("/delete")
                .param("email", "dan@example.com")
                .param("password", "Dan123")
            )
            .andExpect(status().isOk());
    }
}
