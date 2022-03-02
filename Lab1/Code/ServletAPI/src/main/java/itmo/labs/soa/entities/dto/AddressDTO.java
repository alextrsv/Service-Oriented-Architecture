package itmo.labs.soa.entities.dto;

import itmo.labs.soa.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO extends AbstractDTO{

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String zipCode; //Длина строки должна быть не меньше 7, Поле может быть null
    private LocationDTO town; //Поле может быть null

    public void setTown(Location town) {
        this.town = (LocationDTO) town.mapToDto();
    }
}
