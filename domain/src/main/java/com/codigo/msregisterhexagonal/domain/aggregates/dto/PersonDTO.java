package com.codigo.msregisterhexagonal.domain.aggregates.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    private String numDocPerson;
    private String typeDocPerson;
    private String firstNamePerson;
    private String lastNamePerson;
    private Integer statusPerson;
    private String userCreatePerson;
    private Timestamp dateCreatePerson;

}
