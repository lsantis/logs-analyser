package com.assignment.service.implementation;

import com.assignment.domain.dto.EventLog;
import com.assignment.domain.entity.Event;
import com.assignment.service.Parser;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class LogParser implements Parser {

    @Override
    public List<Event> parseFile(String filePath) throws FileNotFoundException {
        log.info("LogParser parseFile started");

        StringBuilder eventLog = new StringBuilder();
        FileInputStream inputStream = null;
        Scanner scanner = null;
        List<EventLog> eventLogList = new ArrayList<>();

        try {
            inputStream = new FileInputStream(filePath);
            scanner = new Scanner(inputStream, "UTF-8");

            while (scanner.hasNextLine()) {
                if(!(eventLog.length() == 0) && (eventLog.substring(eventLog.length() -1).equals("}"))) {
                    eventLog.setLength(0);
                }
                eventLog.append(scanner.nextLine());

                if (!eventLog.substring(eventLog.length() -1).equals("}")) {
                    eventLog.append(scanner.nextLine());
                }
                eventLogList.add(parseEventLog(eventLog));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (scanner != null) {
                scanner.close();
            }
        }
        log.info("LogParser parseFile finished");
        return buildEventList(eventLogList);
    }

    private EventLog parseEventLog(StringBuilder eventLog) throws ParseException {

        JSONParser jsonParser = new JSONParser();

        Object obj = jsonParser.parse(eventLog.toString());

        JSONObject eventObject = (JSONObject) obj;

        EventLog eventLogDto = EventLog.builder()
                .id((String) eventObject.get("id"))
                .state((String) eventObject.get("state"))
                .type((String) eventObject.get("type"))
                .host((String) eventObject.get("host"))
                .timestamp((long) eventObject.get("timestamp"))
                .build();

        return eventLogDto;
    }

    private List<Event> buildEventList(List<EventLog> eventLogList) {
        log.info("LogParser.buildEventList method starting");

        Map<String, EventLog> eventLogMap = new HashMap<>();
        List<Event> eventList = new ArrayList<>();
        Event event;
        long eventDuration;
        boolean alert;

        for (EventLog eventLogDto : eventLogList) {
            if (eventLogMap.containsKey(eventLogDto.getId())) {
                eventDuration = Math.abs(eventLogMap.get(eventLogDto.getId()).getTimestamp() - eventLogDto.getTimestamp());
                alert = (eventDuration > 4) ? true : false;

                event = Event.builder()
                        .eventId(eventLogDto.getId())
                        .eventDuration(eventDuration)
                        .type(eventLogDto.getType())
                        .host(eventLogDto.getHost())
                        .alert(alert)
                        .build();
                eventList.add(event);
            } else {
                eventLogMap.put(eventLogDto.getId(), eventLogDto);
            }
        }
        log.info("LogParser.buildEventList method completed");
        return eventList;
    }
}