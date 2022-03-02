package soa.labs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO implements Serializable {
    String id;
    String annualTurnover;
    String employeesCount;
}
