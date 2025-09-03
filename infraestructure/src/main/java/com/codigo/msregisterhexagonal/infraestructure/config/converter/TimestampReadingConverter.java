package com.codigo.msregisterhexagonal.infraestructure.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.sql.Timestamp;
import java.util.Date;

@ReadingConverter
public class TimestampReadingConverter implements Converter<Date, Timestamp> {
    @Override
    public Timestamp convert(Date source) {
        return (source == null) ? null : new Timestamp(source.getTime());
    }
}

