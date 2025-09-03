package com.codigo.msregisterhexagonal.infraestructure.adapters;

import com.codigo.msregisterhexagonal.domain.aggregates.dto.PersonDTO;
import com.codigo.msregisterhexagonal.domain.ports.out.PersonServiceOut;
import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntity;
import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntityDoc;
import com.codigo.msregisterhexagonal.infraestructure.repository.PersonRepository;
import com.codigo.msregisterhexagonal.infraestructure.repository.PersonRepositoryDoc;
import com.codigo.msregisterhexagonal.infraestructure.response.ResponseReniec;
import com.codigo.msregisterhexagonal.infraestructure.rest.ReniecClient;
import com.codigo.msregisterhexagonal.infraestructure.retrofit.ClientReniecServiceRetrofit;
import com.codigo.msregisterhexagonal.infraestructure.retrofit.ClienteReniecRetrofit;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@Log4j2
public class PersonAdapter implements PersonServiceOut {

    private final PersonRepository personRepository;
    private final PersonRepositoryDoc personRepositoryDoc;
    private final ReniecClient reniecClient;
    private final ModelMapper personMapper;
    private final ModelMapper reniecMapper;
    private final ModelMapper reniecMapperDoc;
    private final RestTemplate restTemplate;

    ClienteReniecRetrofit retrofitPreConfig = ClientReniecServiceRetrofit
            .getNewRetrofit()
            .create(ClienteReniecRetrofit.class);

    @Value("${token}")
    private String token;

    public PersonAdapter(PersonRepository personRepository,
                         PersonRepositoryDoc personRepositoryDoc, ReniecClient reniecClient,
                         @Qualifier("defaulMapper") ModelMapper personMapper,
                         @Qualifier("reniecMapper") ModelMapper reniecMapper,
                         @Qualifier("reniecMapperDoc") ModelMapper reniecMapperDoc, RestTemplate restTemplate) {
        this.personRepository = personRepository;
        this.personRepositoryDoc = personRepositoryDoc;
        this.reniecClient = reniecClient;
        this.personMapper = personMapper;
        this.reniecMapper = reniecMapper;
        this.reniecMapperDoc = reniecMapperDoc;
        this.restTemplate = restTemplate;
    }

    @Override
    public PersonDTO createPersonOut(String dni) throws IOException {
        log.info("Inicio - createPersonOut - DNI: {}", dni);
        return Optional.of(getEntity(dni))
                .map(personRepositoryDoc::save)
                .map(this::mapToDtoDoc)
                .orElseThrow(() -> new RuntimeException("ERROR AL GUARDAR LA PERSONA CON DNI: " + dni));
    }

    @Override
    public PersonDTO getPersonOut(String dni) throws IOException {
        log.info("Inicio - getPersonOut - DNI: {}", dni);
        Optional<PersonEntityDoc> person = personRepositoryDoc.findByNumDoc(dni);  // Cambia a findByNumDoc
        return person.map(this::mapToDtoDoc)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada con DNI: " + dni));
    }

    @Override
    public PersonDTO updatePersonOut(String dni, PersonDTO personDTO) throws IOException {
        log.info("Inicio - updatePersonOut - DNI: {}", dni);
        // Buscar el documento por numDoc
        Optional<PersonEntityDoc> optionalPerson = personRepositoryDoc.findByNumDoc(dni);
        if (optionalPerson.isEmpty()) {
            log.error("Persona no encontrada con DNI: {}", dni);
            throw new RuntimeException("Persona no encontrada con DNI: " + dni);
        }

        // Obtener la entidad existente
        PersonEntityDoc personEntityDoc = optionalPerson.get();

        // Actualizar los campos con los valores del DTO
        personEntityDoc.setFirstName(personDTO.getFirstNamePerson());
        personEntityDoc.setLastName(personDTO.getLastNamePerson());
        personEntityDoc.setTypeDoc(personDTO.getTypeDocPerson());
        personEntityDoc.setNumDoc(personDTO.getNumDocPerson()); // Nota: Cuidado si cambias el DNI
        personEntityDoc.setStatus(personDTO.getStatusPerson());
        personEntityDoc.setUserCreate(personDTO.getUserCreatePerson()); // O userUpdate si aplica
        personEntityDoc.setDateCreate(new Timestamp(System.currentTimeMillis())); // Actualiza la fecha

        // Guardar la entidad actualizada
        try {
            PersonEntityDoc updatedEntity = personRepositoryDoc.save(personEntityDoc);
            log.info("Persona actualizada con DNI: {}", dni);
            return personMapper.map(updatedEntity, PersonDTO.class);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            log.error("Error: DNI duplicado al actualizar: {}", dni);
            throw new RuntimeException("El DNI " + personDTO.getNumDocPerson() + " ya está registrado");
        } catch (Exception e) {
            log.error("Error al guardar la entidad: {}", e.getMessage());
            throw new IOException("Error al actualizar la persona en la base de datos", e);
        }
    }

    @Override
    public void deletePersonOut(String dni) throws IOException {
        log.info("Inicio - deletePersonOut - DNI: {}", dni);
        Optional<PersonEntityDoc> person = personRepositoryDoc.findByNumDoc(dni);
        if (person.isEmpty()) {
            log.error("Persona no encontrada con DNI: {}", dni);
            throw new RuntimeException("Persona no encontrada con DNI: " + dni);
        }
        personRepositoryDoc.delete(person.get());
        log.info("Persona eliminada con DNI: {}", dni);
    }

    private PersonEntityDoc getEntity(String dni) throws IOException {
        log.info("GetEntity para dni: {}", dni);
        ResponseReniec responseReniec = executeRestTemplate(dni); // Usando RestTemplate como ejemplo
        log.info("Respuesta de Reniec: {}", responseReniec);
        if (responseReniec == null || responseReniec.getNumeroDocumento() == null) {
            throw new RuntimeException("Respuesta inválida de RENIEC: " + dni);
        }
        PersonEntityDoc person = reniecMapperDoc.map(responseReniec, PersonEntityDoc.class);
        person.setStatus(1);
        person.setUserCreate("PRODRIGUEZ");
        person.setDateCreate(new Timestamp(System.currentTimeMillis()));
        return person;
    }

    private ResponseReniec executeReniec(String dni) {
        String header = "Bearer " + token;
        log.info("Consultando RENIEC para dni: {}", dni);
        return Optional.ofNullable(reniecClient.getInfoReniec(dni, header))
                .orElseThrow(() -> new RuntimeException("Error al consultar la persona"));
    }

    private PersonEntity mapReniecToEntity(ResponseReniec responseReniec) {
        return reniecMapper.map(responseReniec, PersonEntity.class);
    }

    private PersonDTO mapToDto(PersonEntity personEntity) {
        return personMapper.map(personEntity, PersonDTO.class);
    }

    private PersonDTO mapToDtoDoc(PersonEntityDoc personEntityDoc) {
        return personMapper.map(personEntityDoc, PersonDTO.class);
    }

    private ResponseReniec executeReniecRetrofit(String dni) throws IOException {
        Response<ResponseReniec> executeReniec = preparacionRetrofitParams(dni).execute();
        if (executeReniec.isSuccessful() && executeReniec.body() != null) {
            return executeReniec.body();
        }
        return new ResponseReniec();
    }

    private Call<ResponseReniec> preparacionRetrofitParams(String dni) {
        return retrofitPreConfig.getInfoReniec(token, dni);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private ResponseReniec executeRestTemplate(String dni) {
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + dni;
        try {
            HttpEntity<?> cabeceras = new HttpEntity<>(createHeaders());
            ResponseEntity<ResponseReniec> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    cabeceras,
                    ResponseReniec.class
            );
            if (response.getStatusCode() == org.springframework.http.HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("Error al consultar el servicio externo para el DNI: {}", dni, e);
        }
        return null;
    }
}