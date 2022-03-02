package itmo.labs.soa.entities.enums;

public enum Status {
    FIRED("fired"),
    HIRED("hired"),
    RECOMMENDED_FOR_PROMOTION("recommended_for_promotion"),
    REGULAR("regular"),
    PROBATION("probation");

    String status;

    Status(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}