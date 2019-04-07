package com.assignment.service;

import com.assignment.domain.entity.Event;

import java.io.FileNotFoundException;
import java.util.List;

public interface Parser {

    List<Event> parseFile(String filePath) throws FileNotFoundException;
}