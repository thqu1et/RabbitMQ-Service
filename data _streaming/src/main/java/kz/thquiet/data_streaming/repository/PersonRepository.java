package kz.thquiet.data_streaming.repository;

import kz.thquiet.data_streaming.entity.PersonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonsEntity, Integer> {
}
