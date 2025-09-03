package com.codigo.msregisterhexagonal.domain.ports.in;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDTO;

public interface PersonServiceIn {
    PersonDTO createPersonIn(String dni);
    PersonDTO getPersonIn(String dni);
    PersonDTO updatePersonIn(String dni, PersonDTO personDTO);
    void deletePersonIn(String dni);
}