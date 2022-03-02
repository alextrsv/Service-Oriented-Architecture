package itmo.soa.baseservice.entities;

import itmo.soa.baseservice.entities.dto.AbstractDTO;
import itmo.soa.baseservice.entities.enums.Position;
import itmo.soa.baseservice.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "worker2")
public class Worker extends AbstractEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//, generator="vehicle-gen")
    private Integer id;

    @Column(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(optional = false, cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates; //Поле не может быть null

    @Column(name = "creationdate")
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @Column(name = "salary")
    private Double salary; //Поле не может быть null, Значение поля должно быть больше 0
    @Column(name = "startdate")
    private LocalDate startDate; //Поле не может быть null
    @Column(name = "position")
    private Position position; //Поле может быть null
    @Column(name = "status")
    private Status status; //Поле может быть null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization; //Поле может быть null

    public Worker(String name, Coordinates coordinates, LocalDate creationDate, Double salary,
                  LocalDate startDate, Position position, Status status, Organization organization) {
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