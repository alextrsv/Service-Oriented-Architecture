package itmo.soa.baseservice.utils;

import itmo.soa.baseservice.entities.dto.NewWorkerReqParam;
import itmo.soa.baseservice.exception.NoSuchPositionException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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
        if(!newWorkerReqParam.getPosition().equals("MANAGER") && !newWorkerReqParam.getPosition().equals("ENGINEER") &&
                !newWorkerReqParam.getPosition().equals("BAKER") && !newWorkerReqParam.getPosition().equals("MANAGER_OF_CLEANING") ){
            errors.reject("position","no such position");
            throw new NoSuchPositionException();
        }
    }
}
