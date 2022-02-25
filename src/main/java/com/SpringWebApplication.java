package com;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebApplication {

    public static void main(String[] args) {
        Logger.getLogger(SpringWebApplication.class).info("Starting Spring web application " + java.time.ZonedDateTime.now());
        SpringApplication.run(SpringWebApplication.class, args);
        BasicConfigurator.configure();
    }
}
