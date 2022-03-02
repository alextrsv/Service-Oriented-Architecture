package itmo.labs.soa.entities;

import itmo.labs.soa.entities.dto.AbstractDTO;
import itmo.labs.soa.entities.enums.Position;
import itmo.labs.soa.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "worker")
public class Worker extends AbstractEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//, generator="vehicle-gen")
    private Integer id;

    @Column(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(optional = false, cascade =  CascadeType.PERSIST)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates; //Поле не может быть null

    @Column(name = "creationDate")
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Column(name = "salary")
    private Double salary; //Поле не может быть null, Значение поля должно быть больше 0
    @Column(name = "startDate")
    private java.time.LocalDateTime startDate; //Поле не может быть null
    @Column(name = "position")
    private Position position; //Поле может быть null
    @Column(name = "status")
    private Status status; //Поле может быть null

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "organization_id")
    private Organization organization; //Поле может быть null

    public Worker(String name, Coordinates coordinates, LocalDateTime creationDate, Double salary,
                  LocalDateTime startDate, Position position, Status status, Organization organization) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.startDate = startDate;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }


    @Override
    public AbstractDTO mapToDto() {
        return new WorkerDTOBuilder()
                .setId(id)
                .setName(name)
                .setCreationDate(String.valueOf(creationDate))
                .setSalary(salary)
                .setStartDate(String.valueOf(startDate))
                .setPosition(position)
                .setStatus(status)
                .setCoordinates(coordinates)
                .setOrganization(organization)
                .build();
    }
}