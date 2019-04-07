package com.assignment.service.implementation;

import com.assignment.domain.entity.Event;
import com.assignment.repository.EventRepository;
import com.assignment.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void createEvent(Event event) {
        eventRepository.save(event);
    }
}