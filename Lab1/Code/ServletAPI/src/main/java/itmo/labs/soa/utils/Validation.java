package itmo.labs.soa.utils;

import itmo.labs.soa.entities.enums.Position;
import itmo.labs.soa.entities.enums.Status;
import itmo.labs.soa.exception.InvalidIDException;
import itmo.labs.soa.exception.NegativeValueException;
import itmo.labs.soa.exception.NoSuchPositionException;
import itmo.labs.soa.exception.ValueBeyondLimit;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Validation {

    public double checkDouble(String value, String paramName) throws Exception {

        double number;
        try {
            number = Double.parseDouble(value);
        }
        catch(Exception e) {
            throw new Exception(String.format("%s should be a number", paramName));
        }
        if(value == null) {
            throw new Exception(String.format("%s should be != null", paramName));
        }
        return number;
    }

    public LocalDateTime handleDateTime(String value, String paramName) throws InvalidIDException {

        value += " 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(value, formatter);
        }catch (Exception ex){
            throw new InvalidIDException("test invalid ");
        }
    }

    @SneakyThrows
    public Position handlePosition(String value, String paramName) {
//        if (Arrays.asList(Position.values()).contains(value)) return Position.valueOf(value);
        if(!value.equals("MANAGER") && !value.equals("ENGINEER") &&
                !value.equals("BAKER") && !value.equals("MANAGER_OF_CLEANING") ){
            throw new NoSuchPositionException();
        }
        else return Position.valueOf(value);
    }

    public Status handleStatus(String value, String paramName) {
        return Status.valueOf(value);
    }

    public Integer checkInteger(String value, String paramName) throws Exception {
        int number;
        try {
            number = Integer.parseInt(value);
        }
        catch(Exception e) {
            throw new InvalidIDException(value);
        }
        return number;
    }


    @SneakyThrows
    public Double checkSalary(String s, String salaryParamName) {

        double salary = this.checkDouble(s, salaryParamName);
        if (salary < 0) throw new NegativeValueException("salary should be positive");
        else return salary;
    }

    public String normalizeData(String value){
        return value.substring(0, 10);
    }

    @SneakyThrows
    public Integer checkId(String value){
        if (this.checkInteger(value, "id") < 0) throw new NegativeValueException("id should be positive");
        else return this.checkInteger(value, "id");
    }

    @SneakyThrows
    public Integer checkPage(String value){
        int pageN = this.checkInteger(value, "");
        if (pageN >= 0) return pageN;
        else throw new NegativeValueException("page number should be >= 0");
    }

    @SneakyThrows
    public Double checkCoord(Double value, String paramName, Double maxValue){
        if (value >= maxValue) throw new ValueBeyondLimit(paramName + "should be <=" + maxValue);
        else return value;
    }
}
