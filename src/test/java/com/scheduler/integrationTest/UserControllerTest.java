package com.scheduler.integrationTest;

import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateUser() throws Exception {

        mockMvc.perform(
                post("/v1/users")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content("""
                                        {
                                            "name":"Alex",
                                            "email":"alex@test.com"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@test.com"));
    }

    @Test
    void shouldReturnAvailability() throws Exception {

        mockMvc.perform(
                        get("/v1/users/alex@test.com/availability")
                                .param("from", "2026-06-01T00:00:00")
                                .param("to", "2026-06-30T23:59:59")
                )
                .andExpect(status().isOk());
    }
}
