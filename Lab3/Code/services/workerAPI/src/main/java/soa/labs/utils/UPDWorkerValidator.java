package soa.labs.utils;

import soa.labs.entities.dto.*;
import soa.labs.exception.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UPDWorkerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UPDWorkerParam.class.equals(clazz);
    }

    @SneakyThrows
    @Override
    public void validate(Object target, Errors errors) {
        UPDWorkerParam updWorkerParam = (UPDWorkerParam) target;
        if (updWorkerParam.getPosition() != null && !updWorkerParam.getPosition().equals("MANAGER") && !updWorkerParam.getPosition().equals("ENGINEER") &&
                !updWorkerParam.getPosition().equals("BAKER") && !updWorkerParam.getPosition().equals("MANAGER_OF_CLEANING")) {
            errors.reject("position", "no such position");
            throw new NoSuchPositionException();
        }
    }
}
