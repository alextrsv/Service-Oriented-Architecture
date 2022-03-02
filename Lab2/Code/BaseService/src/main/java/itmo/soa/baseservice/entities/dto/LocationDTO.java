package itmo.soa.baseservice.entities.dto;

import lombok.Data;

@Data
public class LocationDTO extends AbstractDTO {

    private int id;
    private Float x; //Поле не может быть null
    private Integer y; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 679, Поле не может быть null

}
