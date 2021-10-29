package ru.job4j.tracker.hibernate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;

/**
 * LongTimestampConverter.
 */
@Converter(autoApply = true)
public class LongTimestampConverter implements AttributeConverter<Long, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(Long aLong) {
        return new Timestamp(aLong);
    }

    @Override
    public Long convertToEntityAttribute(Timestamp timestamp) {
        return timestamp.getTime();
    }
}
