package com.codigo.msregisterhexagonal.infraestructure.repository;

import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntityDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PersonRepositoryDoc extends MongoRepository<PersonEntityDoc, Long> {
    Optional<PersonEntityDoc> findByNumDoc(String numDoc);
}