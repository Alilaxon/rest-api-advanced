package com.epam.esm.model.confing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Configuration
public class Config {

    @Bean
    DateTimeFormatter dateTimeFormatter() {
        return new DateTimeFormatterBuilder()
                .appendPattern("M-d-yyyy[ h:mm:ss]")
                .toFormatter();
    }

}
