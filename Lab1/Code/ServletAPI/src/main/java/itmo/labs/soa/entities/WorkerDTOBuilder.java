package itmo.labs.soa.entities;

import itmo.labs.soa.entities.dto.CoordinatesDTO;
import itmo.labs.soa.entities.dto.OrganizationDTO;
import itmo.labs.soa.entities.dto.WorkerDTO;
import itmo.labs.soa.entities.enums.Position;
import itmo.labs.soa.entities.enums.Status;

public class WorkerDTOBuilder {
    private Integer id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double salary; //Поле не может быть null, Значение поля должно быть больше 0
    private String startDate; //Поле не может быть null
    private Position position; //Поле может быть null
    private Status status; //Поле может быть null
    private CoordinatesDTO coordinatesDTO;
    private OrganizationDTO organizationDTO; //Поле может быть null



    public WorkerDTOBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    WorkerDTOBuilder setName(String name){
        this.name = name;
        return this;
    }

    WorkerDTOBuilder setCreationDate(String date){
        this.creationDate = creationDate;
        return this;
    }

    WorkerDTOBuilder setSalary(Double salary){
        this.salary = salary;
        return this;
    }

    WorkerDTOBuilder setStartDate(String startDate){
        this.startDate = startDate;
        return this;
    }

    WorkerDTOBuilder setPosition(Position position){
        this.position = position;
        return this;
    }

    WorkerDTOBuilder setStatus(Status status){
        this.status = status;
        return this;
    }

    WorkerDTOBuilder setCoordinates(Coordinates coordinates){
        this.coordinatesDTO = (CoordinatesDTO) coordinates.mapToDto();
        return this;
    }

    WorkerDTOBuilder setOrganization(Organization organization){
        this.organizationDTO = (OrganizationDTO) organization.mapToDto();
        return this;
    }

    WorkerDTO build(){
        return new WorkerDTO(id, name, creationDate, salary, startDate,
                position, status, coordinatesDTO, organizationDTO);
    }

}
