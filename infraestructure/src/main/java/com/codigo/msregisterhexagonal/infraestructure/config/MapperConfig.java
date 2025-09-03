package com.codigo.msregisterhexagonal.infraestructure.config;

import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntity;
import com.codigo.msregisterhexagonal.infraestructure.entity.PersonEntityDoc;
import com.codigo.msregisterhexagonal.infraestructure.response.ResponseReniec;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean(name = "defaulMapper")
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }
    @Bean(name = "reniecMapper")
    public ModelMapper reniecMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //PERSONALINZANDO MAPEO PARA ESCRITURA
        mapper.createTypeMap(ResponseReniec.class, PersonEntity.class)
                .addMapping(ResponseReniec::getNombres, (dest, v) -> dest.setFirstName((String) v))
                .addMapping(ResponseReniec::getApellidoPaterno, (dest, v) -> dest.setLastName((String) v))
                .addMapping(ResponseReniec::getNumeroDocumento, (dest, v) -> dest.setNumDoc((String) v))
                .addMapping(ResponseReniec::getTipoDocumento, (dest, v) -> dest.setTypeDoc((String) v));

        return mapper;
    }
    @Bean(name = "reniecMapperDoc")
    public ModelMapper reniecMapperDoc(){
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //PERSONALINZANDO MAPEO PARA ESCRITURA
        mapper.createTypeMap(ResponseReniec.class, PersonEntityDoc.class)
                .addMapping(ResponseReniec::getNombres, (dest, v) -> dest.setFirstName((String) v))
                .addMapping(ResponseReniec::getApellidoPaterno, (dest, v) -> dest.setLastName((String) v))
                .addMapping(ResponseReniec::getNumeroDocumento, (dest, v) -> dest.setNumDoc((String) v))
                .addMapping(ResponseReniec::getTipoDocumento, (dest, v) -> dest.setTypeDoc((String) v));

        return mapper;
    }
}
