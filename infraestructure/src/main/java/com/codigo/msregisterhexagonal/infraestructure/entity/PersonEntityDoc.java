package com.codigo.msregisterhexagonal.infraestructure.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
import java.util.UUID;

@Document(collection = "persons")
@Data
public class PersonEntityDoc {
    @Id
    private Long id;
    @Field(name = "num_doc")
    @Indexed(unique = true)
    private String numDoc;
    @Field(name = "type_doc")
    private String typeDoc;
    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    @Field(name = "status")
    private Integer status;
    @Field(name = "user_create")
    private String userCreate;
    @Field(name = "date_create")
    private Timestamp dateCreate;

    public PersonEntityDoc() {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
