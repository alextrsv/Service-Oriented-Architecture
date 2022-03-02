package itmo.soa.baseservice.entities.enums.converters;



import itmo.soa.baseservice.entities.enums.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.getStatus();
    }

    @Override
    public Status convertToEntityAttribute(String status) {
        if (status == null) {
            return null;
        }

        return Stream.of(Status.values())
                .filter(s -> s.getStatus().equals(status))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}