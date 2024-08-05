package kz.thquiet.data_streaming_receiver.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import kz.thquiet.data_streaming_receiver.dto.PersonsDTO;
import kz.thquiet.data_streaming_receiver.entity.PersonsEntity;
import kz.thquiet.data_streaming_receiver.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitReceiver {

    private final PersonRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitReceiver.class);

    @RabbitListener(queues = {"JsonQueue"})
    public void receiveJson(String data) {
        LOGGER.info("Received: {}", data);
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<PersonsDTO> persons = Arrays.asList(mapper.readValue(data, PersonsDTO[].class));
            persons.forEach(person -> {
                save(person);
            });
        } catch (Exception e) {
            LOGGER.error("Error processing message: ", e);
        }
    }

    private void save(PersonsDTO dto) {
        if (repository.existsById(dto.getId())) {
            LOGGER.info(String.format("Entity with ID %d already exists, skipping...", dto.getId()));
            return;
        }
        PersonsEntity en = new PersonsEntity();
        en.setId(dto.getId());
        en.setAddress(dto.getAddress());
        en.setFullName(dto.getFullName());
        en.setEmail(dto.getEmail());
        repository.save(en);
        LOGGER.info(String.format("json message received and saved -> %s", en));

    }
}