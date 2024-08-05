package kz.thquiet.data_streaming_receiver.repository;

import kz.thquiet.data_streaming_receiver.entity.PersonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonsEntity, Integer> {
}
