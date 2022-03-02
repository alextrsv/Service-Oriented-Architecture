package org.example.util;

import lombok.SneakyThrows;
import org.example.exception.InvalidIDException;
import org.example.exception.NegativeValueException;
import org.example.exception.NoSuchPositionException;

public class Validation {

    @SneakyThrows
    public void handlePosition(String value, String paramName) {
        if(!value.equals("MANAGER") && !value.equals("ENGINEER") &&
                !value.equals("BAKER") && !value.equals("MANAGER_OF_CLEANING") ){
            throw new NoSuchPositionException();
        }
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
    public Integer checkId(String value){
        if (this.checkInteger(value, "id") < 0) throw new NegativeValueException("id should be positive");
        else return this.checkInteger(value, "id");
    }

}
