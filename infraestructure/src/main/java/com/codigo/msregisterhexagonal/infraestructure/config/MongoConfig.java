package com.codigo.msregisterhexagonal.infraestructure.config;

import com.codigo.msregisterhexagonal.infraestructure.config.converter.TimestampReadingConverter;
import com.codigo.msregisterhexagonal.infraestructure.config.converter.TimestampWritingConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "hexagonalG9";  // Nombre de tu BD desde application.properties
    }

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new TimestampReadingConverter(),
                new TimestampWritingConverter()
        ));
    }
}