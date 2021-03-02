package com.safetynet.alerts.service;

import com.safetynet.alerts.interfaces.IDataLoaderService;
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


    }

}



