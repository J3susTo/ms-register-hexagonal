package com.codigo.msregisterhexagonal.domain.ports.out;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDTO;

import java.io.IOException;

public interface PersonServiceOut {
    PersonDTO createPersonOut(String dni) throws IOException;
    PersonDTO getPersonOut(String dni) throws IOException;
    PersonDTO updatePersonOut(String dni, PersonDTO personDTO) throws IOException;
    void deletePersonOut(String dni) throws IOException;
}