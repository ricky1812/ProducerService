package com.example.producerservice.controllers;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String sendEvent(@RequestBody String message) {
        kafkaTemplate.send("eventhub", message);
        return "Sent: " + message;
    }
}
