package itmo.labs.soa.entities;

import itmo.labs.soa.entities.dto.AbstractDTO;
import itmo.labs.soa.entities.dto.CoordinatesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordinates")
public class Coordinates extends AbstractEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//, generator="vehicle-gen")
    private int id;

    @Column(name = "x_coord")
    private double x; //Максимальное значение поля: 271
    @Column(name = "y_coord")
    private Double y; //Максимальное значение поля: 57, Поле не может быть null


    @OneToMany(mappedBy = "coordinates", fetch = FetchType.LAZY)
    private List<Worker> workers;

    public Coordinates(double x, Double y){
        this.x = x;
        this.y = y;
    }


    @Override
    public AbstractDTO mapToDto() {
        CoordinatesDTO dto = new CoordinatesDTO();
        dto.setX(this.x);
        dto.setY(this.y);
        return dto;
    }
}