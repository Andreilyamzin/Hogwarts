package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/info")

public class InfoController {
    private final static Logger logger = Logger.getLogger(InfoController.class.getName());
    @Value("${server.port}")
    private int port;

    @GetMapping("/port")
    public int port(){
        logger.info("Server port: " + port);
        return port;
    }
}
