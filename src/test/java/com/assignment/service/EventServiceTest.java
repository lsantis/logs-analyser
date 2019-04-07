package com.assignment.service;

import com.assignment.domain.entity.Event;
import com.assignment.repository.EventRepository;
import com.assignment.service.implementation.EventServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private static final String EVENT_ID = "scsmbstgra";

    // Class being tested
    @Autowired
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    private Event event;

    @Before
    public void setUp() {
        eventService = new EventServiceImpl(eventRepository);
    }

    @Test
    public void shouldCreateEventRecordInTheDatabase() {
        event = new Event();
        event.setEventId(EVENT_ID);
        event.setEventDuration(50);

        eventService.createEvent(event);

        verify(eventRepository, times(1)).save(event);
    }

}