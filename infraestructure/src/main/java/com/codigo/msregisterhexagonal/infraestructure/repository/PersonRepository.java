package com.codigo.msregisterhexagonal.infraestructure.repository;

import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
