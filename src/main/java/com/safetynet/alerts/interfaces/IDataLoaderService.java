package com.safetynet.alerts.interfaces;

import org.json.simple.parser.ParseException;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.io.IOException;

/**
 * DataLoaderService read the data.json file
 */
public interface IDataLoaderService {
    /**
     * Load data json file on startup.
     *
     * @param event the event
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    void loadDataJsonFileOnStartup(ApplicationReadyEvent event) throws IOException, ParseException;
}

