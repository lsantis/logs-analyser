package com.assignment.bootstrap;

import com.assignment.domain.entity.Event;
import com.assignment.service.EventService;
import com.assignment.service.Parser;
import com.assignment.service.implementation.EventServiceImpl;
import com.assignment.service.implementation.LogParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class BootstrapTest {

    // Class being tested
    @Autowired
    private Bootstrap bootstrap;

    @Mock
    private EventService eventService;

    @Mock
    private Parser logParser;

    @Before
    public void setUp() {
        bootstrap = new Bootstrap(logParser, eventService);
    }

    @Test
    public void runWithValidInputTest() throws FileNotFoundException {
        List<Event> expectedEventList = new ArrayList<>();
        Event event = new Event().builder().eventId("abc").eventDuration(30).build();
        expectedEventList.add(event);
        String args[] = {"events.log"};

        Mockito.when(logParser.parseFile(Mockito.anyString())).thenReturn(expectedEventList);

        bootstrap.run(args);

        verify(logParser, times(1)).parseFile(Mockito.anyString());
        verify(eventService, times(1)).createEvent(event);
    }

    @Test
    public void runWithInvalidInputTest() throws FileNotFoundException {
        List<Event> expectedEventList = new ArrayList<>();
        Event event = new Event().builder().eventId("abc").eventDuration(30).build();
        expectedEventList.add(event);
        String args[] = {"events"};

        bootstrap.run(args);

        verify(logParser, times(0)).parseFile(Mockito.anyString());
        verify(eventService, times(0)).createEvent(event);
    }

}
