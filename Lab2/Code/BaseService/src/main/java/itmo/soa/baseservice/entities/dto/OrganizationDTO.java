package itmo.soa.baseservice.entities.dto;


import itmo.soa.baseservice.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO extends AbstractDTO {
    int id;
    private Float annualTurnover; //Поле не может быть null, Значение поля должно быть больше 0
    private Long employeesCount; //Поле может быть null, Значение поля должно быть больше 0
    private AbstractDTO officialAddress; //Поле не может быть null


    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress.mapToDto();
    }
}
