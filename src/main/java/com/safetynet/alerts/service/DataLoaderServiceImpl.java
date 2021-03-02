package com.safetynet.alerts.service;

import com.google.gson.Gson;
import com.safetynet.alerts.interfaces.IDataLoaderService;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repositories.PersonRepository;
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
public class DataLoaderServiceImpl implements IDataLoaderService {

    private final PersonRepository personRepository;

    public DataLoaderServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws IOException {
        File file = ResourceUtils.getFile("classpath:data.json");

        //Read File Content
        String content = new String(Files.readAllBytes(file.toPath()));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(content);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // boucler sur les personnes et faire un save a chaque personne trouvée
        // personRepository.save(person);

        Gson gson = new Gson();
        JSONArray persons = (JSONArray) jsonObject.get("persons");

        for (Object personObj : persons) {
            JSONObject personJsonObject = (JSONObject) personObj;
            Person person = gson.fromJson(personJsonObject.toJSONString(), Person.class);
            personRepository.save(person);
        }



    }

}



