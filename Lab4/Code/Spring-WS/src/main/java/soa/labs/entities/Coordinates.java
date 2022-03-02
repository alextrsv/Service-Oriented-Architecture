package soa.labs.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import soa.labs.entities.dto.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordinates")
public class Coordinates extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//, generator="vehicle-gen")
    private int id;

    @Column(name = "x_coord")
    private double x; //Максимальное значение поля: 271
    @Column(name = "y_coord")
    private Double y; //Максимальное значение поля: 57, Поле не может быть null


    @JsonIgnore
    @OneToMany(mappedBy = "coordinates", fetch = FetchType.LAZY)
    private List<Worker> workers;

    public Coordinates(double x, Double y) {
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