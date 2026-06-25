package com.scheduler.integrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                                  "name":"John",
                                  "email":"john@test.com"
                                }
                                """)
        ).andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(
                        post("/v1/users/john@test.com/slots")
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
