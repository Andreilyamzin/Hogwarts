package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    @GetMapping("/sum")
    public int sum(){
        var start = System.currentTimeMillis();
       // int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        int sum = IntStream.iterate(1,a -> a +1).limit(1_000_000).parallel().sum();
        var end = System.currentTimeMillis() - start ;
        logger.info("Elapsed time: {} ms");
        return sum;
    }
}
