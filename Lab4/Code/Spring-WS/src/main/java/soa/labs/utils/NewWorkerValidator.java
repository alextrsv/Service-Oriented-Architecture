package soa.labs.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import soa.labs.entities.dto.*;
import soa.labs.exception.*;

@Component
public class NewWorkerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NewWorkerReqParam.class.equals(clazz);
    }

    @SneakyThrows
    @Override
    public void validate(Object target, Errors errors) {
        NewWorkerReqParam newWorkerReqParam = (NewWorkerReqParam) target;
        if (!newWorkerReqParam.getPosition().equals("MANAGER") && !newWorkerReqParam.getPosition().equals("ENGINEER") &&
                !newWorkerReqParam.getPosition().equals("BAKER") && !newWorkerReqParam.getPosition().equals("MANAGER_OF_CLEANING")) {
            errors.reject("position", "no such position");
            throw new NoSuchPositionException();
        }
    }
}
