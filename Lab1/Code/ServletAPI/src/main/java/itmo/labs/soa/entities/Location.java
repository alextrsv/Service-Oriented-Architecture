package itmo.labs.soa.entities;

import itmo.labs.soa.entities.dto.AbstractDTO;
import itmo.labs.soa.entities.dto.LocationDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "location")
public class Location extends AbstractEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//, generator="vehicle-gen")
    private int id;

    @Column(name = "x_coord")
    private Float x; //Поле не может быть null
    @Column(name = "y_coord")
    private Integer y; //Поле не может быть null
    @Column(name = "name")
    private String name; //Длина строки не должна быть больше 679, Поле не может быть null

    @OneToMany(mappedBy = "town", fetch = FetchType.LAZY)
    private List<Address> addressList;

    @Override
    public AbstractDTO mapToDto() {
        LocationDTO dto = new LocationDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setX(this.x);
        dto.setY(this.y);
        return dto;
    }
}