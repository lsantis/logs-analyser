package com.assignment.service;

import com.assignment.domain.entity.Event;
import com.assignment.service.implementation.LogParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import java.io.FileNotFoundException;
import java.util.List;

@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class LogParserTest {

    // Class being tested
    @Autowired
    private Parser parser;

    @Before
    public void setUp() {
        parser = new LogParser();
    }

    @Test
    public void parseFileTest() throws FileNotFoundException {

        String filePath = "src/test/resources/events.log";

        List<Event> eventList = parser.parseFile(filePath);

        Assert.assertEquals(3, eventList.size());
    }

    @Test(expected = FileNotFoundException.class)
    public void fileShouldNotBeFoundTest() throws FileNotFoundException {

        String filePath = "random_path";

        parser.parseFile(filePath);
    }
}