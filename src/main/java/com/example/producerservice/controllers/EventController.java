package com.example.producerservice.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public EventController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String sendEvent(@RequestBody String message) {
        try{
            JsonNode jsonNode=objectMapper.readTree(message);
            String key= jsonNode.has("type") ? jsonNode.get("type").asText() : "unknown";
            kafkaTemplate.send("eventhub", key,message);
            return "Sent: key " + key+" message: "+message;
        }
        catch (Exception e){
            throw new RuntimeException("Error parsing json message" + e.getMessage());
        }


    }
}
