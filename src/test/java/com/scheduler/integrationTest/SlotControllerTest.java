package com.scheduler.integrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SlotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateSlot() throws Exception {

        mockMvc.perform(
                post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name":"Alex",
                                  "email":"alex@test.com"
                                }
                                """)
        );

        mockMvc.perform(
                        post("/v1/users/alex@test.com/slots")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "startTime":"2026-06-21T09:00:00",
                                            "endTime":"2026-06-21T10:00:00"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slotNumber").value("S1"))
                .andExpect(jsonPath("$.status").value("FREE"));
    }
}
