package com.assignment;

import com.assignment.bootstrap.Bootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    Bootstrap bootstrap;

    public static void main(String args[]) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Use static utility to validate args and delegate to the service LogParser
        bootstrap.run(args);
    }
}