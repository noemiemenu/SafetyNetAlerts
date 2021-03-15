package com.safetynet.alerts.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.safetynet.alerts.model.PersonInfo;
import com.safetynet.alerts.responses.ChildrenWithFamilyResponse;
import com.safetynet.alerts.responses.PersonInfoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PersonTests {

    private static final String jsonPost = "{\n" +
            "      \"firstName\": \"Noemie\",\n" +
            "      \"lastName\": \"Menu\",\n" +
            "      \"address\": \"1509 Culver St\",\n" +
            "      \"city\": \"Culver\",\n" +
            "      \"zip\": \"97451\",\n" +
            "      \"phone\": \"841-874-6530\",\n" +
            "      \"email\": \"Noemie@email.com\"\n" +
            "    }";

    private static final String jsonPut ="{\n" +
            "  \"id\": 60,\n" +
            "  \"firstName\": \"Noemie\",\n" +
            "  \"lastName\": \"Menu\",\n" +
            "  \"address\": \"3012 Culver St\",\n" +
            "  \"city\": \"Culver\",\n" +
            "  \"zip\": \"97451\",\n" +
            "  \"phone\": \"841-874-6530\",\n" +
            "  \"email\": \"Noemie@email.com\"\n" +
            "}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddPerson() throws Exception {
        final ResultActions result =
                mockMvc.perform(
                        post("/person")
                                .content(jsonPost)
                                .contentType(MediaType.APPLICATION_JSON));
        result
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAddPerson_ShouldReturn400_OnPersonAlreadyCreated() throws Exception {
        mockMvc.perform(
                post("/person")
                        .content(jsonPost)
                        .contentType(MediaType.APPLICATION_JSON));

        final ResultActions result =
                mockMvc.perform(
                        post("/person")
                                .content(jsonPost)
                                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePerson() throws Exception {
        mockMvc.perform(
                post("/person")
                        .content(jsonPost)
                        .contentType(MediaType.APPLICATION_JSON));
        final ResultActions result = mockMvc.perform(
                put("/person")
                        .content(jsonPut)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk()).andExpect(content()
                .contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testDeletePerson() throws Exception {
        mockMvc.perform(
                post("/person")
                        .content(jsonPost)
                        .contentType(MediaType.APPLICATION_JSON));
        final ResultActions result = mockMvc.perform(
                delete("/person")
                        .param("firstName", "Noemie")
                        .param("lastName", "Menu"));
        result.andExpect(status().isOk());

    }

    @Test
    public void testDeletePerson_ShouldReturn400_OnPersonAlreadyDeleted() throws Exception {
        final ResultActions result = mockMvc.perform(
                delete("/person")
                        .param("firstName", "Noemie")
                        .param("lastName", "Menu"));
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testGetEmailOfAllPersonsInTheCity() throws Exception {
        final ResultActions result = mockMvc.perform(
                get("/communityEmail").param("city", "Culver")
        );

        final MockHttpServletResponse response =
                result.andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        List<String> personEmail = new ArrayList<>();
        personEmail = gson.fromJson(response.getContentAsString(), personEmail.getClass());

        Assertions.assertTrue(personEmail.size() > 0);
    }

    @Test
    public void testGetChildren() throws Exception {
        final ResultActions result = mockMvc.perform(
                get("/childAlert").param("address", "1509 Culver St")
        );

        final MockHttpServletResponse response =
                result.andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        ChildrenWithFamilyResponse childrenWithFamilyResponse = gson.fromJson(response.getContentAsString(), ChildrenWithFamilyResponse.class);
        Assertions.assertTrue(childrenWithFamilyResponse.getChildren().size() > 0);
        ChildrenWithFamilyResponse.ChildWithFamily childWithFamily = childrenWithFamilyResponse.getChildren().get(0);
        Assertions.assertNotNull(childWithFamily.getChild());
        Assertions.assertNotNull(childWithFamily.getChildAge());
        Assertions.assertTrue(childWithFamily.getFamily().size() > 0);
    }
    @Test
    public void testGetChildren_withNoChildrenInResponse() throws Exception {
        final ResultActions result = mockMvc.perform(
                get("/childAlert").param("address", "AZE")
        );

        final MockHttpServletResponse response =
                result.andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        ChildrenWithFamilyResponse childrenWithFamilyResponse = gson.fromJson(response.getContentAsString(), ChildrenWithFamilyResponse.class);
        Assertions.assertEquals(0, childrenWithFamilyResponse.getChildren().size());

    }

     @Test
    public void testGetPersonInfo() throws Exception {
        final ResultActions result = mockMvc.perform(
                get("/personInfo")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"));

        final MockHttpServletResponse response =
                result.andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        PersonInfoResponse personInfoResponse = gson.fromJson(response.getContentAsString(), PersonInfoResponse.class);
        Assertions.assertNotNull(personInfoResponse.getPeople());

        PersonInfo personInfo = personInfoResponse.getPeople().get(0);
        Assertions.assertTrue(personInfo.getAllergies().size() > 0);
        Assertions.assertTrue(personInfo.getMedications().size() > 0);
        Assertions.assertNotNull(personInfo.getBirthdate());
        Assertions.assertTrue(personInfo.getLastName().length() > 0);
        Assertions.assertTrue(personInfo.getEmail().length() > 0);
        Assertions.assertTrue(personInfo.getAddress().length() > 0);
    }


}
