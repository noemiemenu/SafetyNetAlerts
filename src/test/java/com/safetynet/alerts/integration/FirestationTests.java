package com.safetynet.alerts.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.safetynet.alerts.model.PersonInfoFirestationAddress;
import com.safetynet.alerts.model.PersonInfoWithPhone;
import com.safetynet.alerts.responses.ListOfPersonServedByTheseFireStationResponse;
import com.safetynet.alerts.responses.PersonsInFirestationAddressResponse;
import com.safetynet.alerts.responses.PersonsInFirestationNumberResponse;
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
public class FirestationTests {

    private static final String jsonPost = "{\n" +
            "      \"address\": \"29 14th St\",\n" +
            "      \"station\": \"2\"\n" +
            "    }";

    private static final String jsonPut ="{\n" +
            "  \"id\": 60,\n" +
            "  \"address\": \"29 14th St\",\n" +
            "  \"station\": \"2\"\n" +
            "}";


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddFirestation() throws Exception {
        final ResultActions result =
                mockMvc.perform(
                        post("/firestation")
                                .content(jsonPost)
                                .contentType(MediaType.APPLICATION_JSON));
        result
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAddFirestation_ShouldReturn400_OnFirestationAlreadyCreated() throws Exception {
        mockMvc.perform(
                post("/firestation")
                        .content(jsonPost)
                        .contentType(MediaType.APPLICATION_JSON));

        final ResultActions result =
                mockMvc.perform(
                        post("/firestation")
                                .content(jsonPost)
                                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateFirestation() throws Exception {
        mockMvc.perform(
                post("/firestation")
                        .content(jsonPost)
                        .contentType(MediaType.APPLICATION_JSON));
        final ResultActions result = mockMvc.perform(
                put("/firestation")
                        .content(jsonPut)
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk()).andExpect(content()
                .contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testDeleteFirestation() throws Exception {
        mockMvc.perform(
                post("/firestation")
                        .content(jsonPost)
                        .contentType(MediaType.APPLICATION_JSON));
        final ResultActions result = mockMvc.perform(
                delete("/firestation")
                        .param("address", "29 14th St")
                        .param("station", "2"));
        result.andExpect(status().isOk());

    }

    @Test
    public void testDeleteFirestation_ShouldReturn400_OnFirestationAlreadyDeleted() throws Exception {
        final ResultActions result = mockMvc.perform(
                delete("/firestation")
                        .param("station", "5")
                        .param("address"," " ));
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testGetFirestations() throws Exception {
        final ResultActions result = mockMvc.perform(
                get("/firestations").param("stationNumber", "3")
        );

        final MockHttpServletResponse response =
                result.andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        PersonsInFirestationNumberResponse personsInFirestationNumberResponse = gson.fromJson(response.getContentAsString(), PersonsInFirestationNumberResponse.class);

        Assertions.assertTrue(personsInFirestationNumberResponse.getAdults() > 0);
        Assertions.assertTrue(personsInFirestationNumberResponse.getChildren() > 0);
        Assertions.assertTrue(personsInFirestationNumberResponse.getPersons().size() > 0);
    }

    @Test
    public void testGetPhoneOfPersonToStationNumber() throws Exception {
        final ResultActions result = mockMvc.perform(
                get("/phoneAlert").param("firestation", "3")
        );

        final MockHttpServletResponse response =
                result.andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        List<String> personPhone = new ArrayList<>();
        personPhone = gson.fromJson(response.getContentAsString(), personPhone.getClass());

        Assertions.assertTrue(personPhone.size() > 0);
    }

    @Test
    public void testGetPeopleByFirestationAddress() throws Exception {
        final ResultActions result = mockMvc.perform(
                get("/fire").param("address", "1509 Culver St")
        );

        final MockHttpServletResponse response =
                result.andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        PersonsInFirestationAddressResponse personsInFirestationAddressResponse = gson.fromJson(response.getContentAsString(), PersonsInFirestationAddressResponse.class);
        Assertions.assertTrue(personsInFirestationAddressResponse.getPersons().size() > 0);
        Assertions.assertNotNull(personsInFirestationAddressResponse.getStationNumber());

        PersonInfoFirestationAddress personInfoFirestationAddress = personsInFirestationAddressResponse.getPersons().get(0);
        Assertions.assertNotNull(personInfoFirestationAddress);
        Assertions.assertNotNull(personInfoFirestationAddress.getBirthdate());
        Assertions.assertTrue(personInfoFirestationAddress.getAllergies().size() > 0);
        Assertions.assertNotNull(personInfoFirestationAddress.getMedications().size() > 0);

    }

    @Test
    public void testGetListOfHomes() throws Exception {
        final ResultActions result = mockMvc.perform(
                get("/flood/stations")
                        .param("stations", "3")
                        .param("stations", "2"));

        final MockHttpServletResponse response =
                result.andExpect(status().isOk())
                        .andReturn()
                        .getResponse();

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        ListOfPersonServedByTheseFireStationResponse listOfPersonServedByTheseFireStationResponse = gson.fromJson(response.getContentAsString(), ListOfPersonServedByTheseFireStationResponse.class);
        Assertions.assertTrue(listOfPersonServedByTheseFireStationResponse.getPeople().size() > 0);
        PersonInfoWithPhone personInfoWithPhone = listOfPersonServedByTheseFireStationResponse.getPeople().get(0);
        Assertions.assertNotNull(personInfoWithPhone);
        Assertions.assertNotNull(personInfoWithPhone.getFirstName());
        Assertions.assertNotNull(personInfoWithPhone.getLastName());
        Assertions.assertNotNull(personInfoWithPhone.getPhone());
        Assertions.assertNotNull(personInfoWithPhone.getAddress());
        Assertions.assertNotNull(personInfoWithPhone.getEmail());
        Assertions.assertNotNull(personInfoWithPhone.getBirthdate());
        Assertions.assertTrue(personInfoWithPhone.getMedications().size() > 0);
        Assertions.assertTrue(personInfoWithPhone.getAllergies().size() > 0);
    }

}
