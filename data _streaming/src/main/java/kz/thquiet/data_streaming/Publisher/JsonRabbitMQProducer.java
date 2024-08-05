package kz.thquiet.data_streaming.Publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.thquiet.data_streaming.dto.PersonsDTO;
import kz.thquiet.data_streaming.dto.User;
import kz.thquiet.data_streaming.entity.PersonsEntity;
import kz.thquiet.data_streaming.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonRabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonRabbitMQProducer.class);

    @Value("${rabbit.routing.json.name}")
    private String routingJsonKey;

    @Value("${data.exchange}")
    private String exchange;

    @Value("${rabbit.queue.name}")
    private String JsonQueueName;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    private final PersonRepository repository;

    public JsonRabbitMQProducer(RabbitTemplate rabbitTemplate, PersonRepository repository) {
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    public void sendJsonMessage(User user) {
        LOGGER.info(String.format("json message sent -> %s", user.toString()));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, user);
    }

    public void sendJsonPerson() throws JsonProcessingException {
        List<PersonsEntity> persons = repository.findAll();
        List<PersonsDTO> dtos = persons.stream()
                .map(this::convert)
                .collect(Collectors.toList())
                .subList(0,30);
        LOGGER.info(String.format("json message sent -> %s", dtos));
        rabbitTemplate.convertAndSend("", JsonQueueName, dtos);
    }

    private PersonsDTO convert(PersonsEntity personsEntity){
        return PersonsDTO
                .builder()
                .id(personsEntity.getId())
                .fullName(personsEntity.getFullName())
                .email(personsEntity.getEmail())
                .address(personsEntity.getAddress())
                .build();
    }
}