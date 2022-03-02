package soa.labs.entities.enums.converters;


import soa.labs.entities.enums.Position;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PositionConverter implements AttributeConverter<Position, String> {

    @Override
    public String convertToDatabaseColumn(Position position) {
        if (position == null) {
            return null;
        }
        return position.getPosition();
    }

    @Override
    public Position convertToEntityAttribute(String position) {
        if (position == null) {
            return null;
        }

        return Stream.of(Position.values())
                .filter(s -> s.getPosition().equals(position))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}