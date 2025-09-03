package com.codigo.msregisterhexagonal.infraestructure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "persons")
@Data
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numDoc;
    private String typeDoc;
    private String firstName;
    private String lastName;
    private Integer status;
    private String userCreate;
    private Timestamp dateCreate;

}
