package com.safetynet.alerts.service;

import com.google.gson.Gson;
import com.safetynet.alerts.interfaces.IDataLoaderService;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.FirestationsRepository;
import com.safetynet.alerts.repositories.MedicalRecordsRepository;
import com.safetynet.alerts.repositories.PersonsRepository;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Component
@AllArgsConstructor
public class DataLoaderServiceImpl implements IDataLoaderService {

    private final PersonsRepository personsRepository;

    private final FirestationsRepository firestationsRepository;

    private final MedicalRecordsRepository medicalRecordsRepository;


    /**
     *
     * @param event
     * @throws IOException
     * @throws ParseException
     */
    @EventListener
    public void loadDataJsonFileOnStartup(ApplicationReadyEvent event) throws IOException, ParseException {
        JSONObject jsonObject = readJsonFile();
        Gson gson = new Gson();

        JSONArray persons = (JSONArray) jsonObject.get("persons");
        for (Object personObj : persons) {
            JSONObject personJsonObject = (JSONObject) personObj;
            Person person = gson.fromJson(personJsonObject.toJSONString(), Person.class);
            personsRepository.save(person);
        }

        JSONArray firestations = (JSONArray) jsonObject.get("firestations");
        for (Object firestationsObj : firestations) {
            JSONObject firestationJsonObject = (JSONObject) firestationsObj;
            Firestation firestation = gson.fromJson(firestationJsonObject.toJSONString(), Firestation.class);
            firestationsRepository.save(firestation);
        }

        JSONArray medicalrecords = (JSONArray) jsonObject.get("medicalrecords");
        for (Object medicalrecordsObj : medicalrecords) {
            JSONObject medicalrecordsJsonObject = (JSONObject) medicalrecordsObj;
            MedicalRecord medicalrecord = gson.fromJson(medicalrecordsJsonObject.toJSONString(), MedicalRecord.class);
            medicalRecordsRepository.save(medicalrecord);
        }
    }

    private JSONObject readJsonFile() throws IOException, ParseException {
        File file = ResourceUtils.getFile("classpath:data.json");

        //Read File Content
        String fileContent = new String(Files.readAllBytes(file.toPath()));
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(fileContent);
    }
}



