package com.codigo.msregisterhexagonal.infraestructure.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.sql.Timestamp;
import java.util.Date;

@WritingConverter
public class TimestampWritingConverter implements Converter<Timestamp, Date> {
    @Override
    public Date convert(Timestamp source) {
        return source;
    }
}