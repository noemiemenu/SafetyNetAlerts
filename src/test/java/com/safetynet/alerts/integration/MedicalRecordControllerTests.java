package com.safetynet.alerts.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class MedicalRecordControllerTests {

    private static final String json = "{\n" +
            "\t\"firstName\": \"Noémie\",\n" +
            "      \"lastName\": \"Menu\",\n" +
            "      \"birthdate\": \"30/07/1996\",\n" +
            "      \"medications\": [\n" +
            "        \"aznol:350mg\",\n" +
            "        \"hydrapermazol:100mg\"\n" +
            "      ],\n" +
            "      \"allergies\": [\n" +
            "        \"nillacilan\"\n" +
            "      ]\n" +
            "}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddMedicalRecord() throws Exception {
        final ResultActions result =
                mockMvc.perform(
                        post("/medicalRecord")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON));
        result
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAddMedicalRecord_ShouldReturn400_OnMedicalRecordAlreadyCreated() throws Exception {
        mockMvc.perform(
                post("/medicalRecord")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));

        final ResultActions result =
                mockMvc.perform(
                        post("/medicalRecord")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }
}
