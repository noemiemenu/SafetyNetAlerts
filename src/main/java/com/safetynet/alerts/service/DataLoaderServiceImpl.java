package com.safetynet.alerts.service;

import com.google.gson.Gson;
import com.safetynet.alerts.interfaces.IDataLoaderService;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.PersonRepository;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Component
@AllArgsConstructor
public class DataLoaderServiceImpl implements IDataLoaderService {

    private final PersonRepository personRepository;

    @EventListener
    public void loadDataJsonFileOnStartup(ApplicationReadyEvent event) throws IOException, ParseException {
        JSONObject jsonObject = readJsonFile();
        Gson gson = new Gson();
        JSONArray persons = (JSONArray) jsonObject.get("persons");
        for (Object personObj : persons) {
            JSONObject personJsonObject = (JSONObject) personObj;
            Person person = gson.fromJson(personJsonObject.toJSONString(), Person.class);
            personRepository.save(person);
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



