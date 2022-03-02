package itmo.labs.soa.entities;

import itmo.labs.soa.entities.dto.AbstractDTO;
import itmo.labs.soa.entities.dto.OrganizationDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "organization")
public class Organization extends AbstractEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//, generator="vehicle-gen")
    private int id;

    @Column(name = "annual_turnover")
    private Float annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    @Column(name = "employees_count")
    private Long employeesCount; //Поле может быть null, Значение поля должно быть больше 0

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address officialAddress; //Поле не может быть null

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Worker> workers;

    @Override
    public AbstractDTO mapToDto() {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(this.id);
        dto.setAnnualTurnover(this.annualTurnover);
        dto.setEmployeesCount(this.employeesCount);
        dto.setOfficialAddress(this.officialAddress);
        return dto;
    }
}