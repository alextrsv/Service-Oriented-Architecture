package soa.labs.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import soa.labs.entities.dto.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//, generator="vehicle-gen")
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(name = "zip_code")
    private String zipCode; //Длина строки должна быть не меньше 7, Поле может быть null

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location town; //Поле может быть null

    @JsonIgnore
    @OneToMany(mappedBy = "officialAddress", fetch = FetchType.LAZY)
    private List<Organization> organizations;

    @Override
    public AbstractDTO mapToDto() {
        AddressDTO dto = new AddressDTO();
        dto.setId(this.id);
        dto.setZipCode(this.zipCode);
        dto.setTown(this.town);
        return dto;
    }

}