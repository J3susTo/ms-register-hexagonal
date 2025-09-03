package com.codigo.msregisterhexagonal.application.controller;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDTO;
import com.codigo.msregisterhexagonal.domain.ports.in.PersonServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/hexagonal/")
@RequiredArgsConstructor
public class PersonController {

    private final PersonServiceIn personServiceIn;

    @PostMapping("/{dni}")
    public ResponseEntity<PersonDTO> createPerson(@PathVariable("dni") String dni) {
        return ResponseEntity.ok(personServiceIn.createPersonIn(dni));
    }

    @GetMapping("/{dni}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable("dni") String dni) {
        return ResponseEntity.ok(personServiceIn.getPersonIn(dni));
    }

    @PutMapping("/{dni}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable("dni") String dni, @RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(personServiceIn.updatePersonIn(dni, personDTO));
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> deletePerson(@PathVariable("dni") String dni) {
        personServiceIn.deletePersonIn(dni);
        return ResponseEntity.noContent().build();
    }
}