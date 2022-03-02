package itmo.labs.soa.entities.dto;

import itmo.labs.soa.entities.enums.Position;
import itmo.labs.soa.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO extends AbstractDTO {

    private Integer id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double salary; //Поле не может быть null, Значение поля должно быть больше 0
    private String startDate; //Поле не может быть null
    private Position position; //Поле может быть null
    private Status status; //Поле может быть null

    private CoordinatesDTO coordinates; //Поле не может быть null
    private OrganizationDTO organization; //Поле может быть null


}
