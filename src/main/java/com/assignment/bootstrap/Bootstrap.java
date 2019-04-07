package com.assignment.bootstrap;

import com.assignment.domain.entity.Event;
import com.assignment.exception.IncorrectFileExtensionException;
import com.assignment.exception.InvalidInputException;
import com.assignment.service.EventService;
import com.assignment.service.Parser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.FileNotFoundException;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class Bootstrap {

    private static final String FILE_EXTENSION = ".log";

    @Autowired
    private Parser parser;

    @Autowired
    private EventService eventService;

    public void run(String args[]) throws FileNotFoundException {
        log.info("Bootstrap.run starting method");

        boolean isValidated = false;
        List<Event> eventList;

        try {
            isValidated = validateInput(args);

            if (isValidated) {
                // Gets events from file
                eventList = parser.parseFile(args[0]);
                // Persist events in the db
                eventList.forEach(event -> eventService.createEvent(event));
            }
        } catch (InvalidInputException ex) {
            log.error("Error validating the input", ex);
        } catch (IncorrectFileExtensionException ex) {
            log.error("Error validating the file", ex);
        } finally {
            log.info("Bootstrap.run method completed");
        }
    }

    private boolean validateInput(String args[]) throws IncorrectFileExtensionException, InvalidInputException {
        log.info("Bootstrap.validateInput method starting");

        if (args.length == 0 || args.length > 1) {
            throw new InvalidInputException("Incorrect file path input");
        } else if (!args[0].endsWith(FILE_EXTENSION)) {
            throw new IncorrectFileExtensionException("Incorrect file extension");
        } else {
            log.info("Bootstrap.validateInput method completed");
            return true;
        }
    }
}