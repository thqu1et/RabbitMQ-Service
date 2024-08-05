package kz.thquiet.data_streaming.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kz.thquiet.data_streaming.Publisher.JsonRabbitMQProducer;
import kz.thquiet.data_streaming.Publisher.RabbitMQProducer;
import kz.thquiet.data_streaming.dto.User;
import kz.thquiet.data_streaming.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/rabbit" , produces = "application/json")
public class SendController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RabbitMQProducer producer;

    @Autowired
    private JsonRabbitMQProducer jsonProducer;

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> sendMessage(@RequestBody User user) {
        jsonProducer.sendJsonMessage(user);
        return ResponseEntity.ok("Message sent");
    }

    @PostMapping("/persons")
    public ResponseEntity<String> sendMessage() throws JsonProcessingException {
        jsonProducer.sendJsonPerson();
        return ResponseEntity.ok("Message sent");
    }
}
