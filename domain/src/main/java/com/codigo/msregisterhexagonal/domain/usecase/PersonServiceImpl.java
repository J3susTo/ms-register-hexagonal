package com.codigo.msregisterhexagonal.domain.usecase;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDTO;
import com.codigo.msregisterhexagonal.domain.ports.in.PersonServiceIn;
import com.codigo.msregisterhexagonal.domain.ports.out.PersonServiceOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PersonServiceImpl implements PersonServiceIn {

    private final PersonServiceOut personServiceOut;
    private String SERVICE_NAME = "PersonServiceImpl";

    @Override
    public PersonDTO createPersonIn(String dni) {
        String nameMethod = "createPersonIn";
        log.info("{} - {} - INICIO", SERVICE_NAME, nameMethod);
        try {
            PersonDTO person = personServiceOut.createPersonOut(dni);
            log.info("{} - {} - FIN", SERVICE_NAME, nameMethod);
            return person;
        } catch (Exception e) {
            log.error("{} - {} - ERROR: {}", SERVICE_NAME, nameMethod, e.getMessage());
            throw new RuntimeException("Error al crear la persona");
        }
    }

    @Override
    public PersonDTO getPersonIn(String dni) {
        String nameMethod = "getPersonIn";
        log.info("{} - {} - INICIO", SERVICE_NAME, nameMethod);
        try {
            PersonDTO person = personServiceOut.getPersonOut(dni);
            log.info("{} - {} - FIN", SERVICE_NAME, nameMethod);
            return person;
        } catch (Exception e) {
            log.error("{} - {} - ERROR: {}", SERVICE_NAME, nameMethod, e.getMessage());
            throw new RuntimeException("Error al obtener la persona");
        }
    }

    @Override
    public PersonDTO updatePersonIn(String dni, PersonDTO personDTO) {
        String nameMethod = "updatePersonIn";
        log.info("{} - {} - INICIO", SERVICE_NAME, nameMethod);
        try {
            PersonDTO updatedPerson = personServiceOut.updatePersonOut(dni, personDTO);
            log.info("{} - {} - FIN", SERVICE_NAME, nameMethod);
            return updatedPerson;
        } catch (Exception e) {
            log.error("{} - {} - ERROR: {}", SERVICE_NAME, nameMethod, e.getMessage());
            throw new RuntimeException("Error al actualizar la persona");
        }
    }

    @Override
    public void deletePersonIn(String dni) {
        String nameMethod = "deletePersonIn";
        log.info("{} - {} - INICIO", SERVICE_NAME, nameMethod);
        try {
            personServiceOut.deletePersonOut(dni);
            log.info("{} - {} - FIN", SERVICE_NAME, nameMethod);
        } catch (Exception e) {
            log.error("{} - {} - ERROR: {}", SERVICE_NAME, nameMethod, e.getMessage());
            throw new RuntimeException("Error al eliminar la persona");
        }
    }
}