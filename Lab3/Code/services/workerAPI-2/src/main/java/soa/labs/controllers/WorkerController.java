package soa.labs.controllers;

import soa.labs.entities.dto.NewWorkerReqParam;
import soa.labs.entities.dto.UPDWorkerParam;
import soa.labs.entities.dto.WorkerDTO;
import soa.labs.services.WorkerService;
import soa.labs.utils.NewWorkerValidator;
import soa.labs.utils.UPDWorkerValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import soa.labs.entities.dto.*;

@Validated
@RestController
@RequestMapping("workers")
public class WorkerController {

    final WorkerService workerService;

    final NewWorkerValidator newWorkerValidator;

    final UPDWorkerValidator updWorkerValidator;


    public WorkerController(WorkerService workerService, NewWorkerValidator newWorkerValidator, UPDWorkerValidator updWorkerValidator) {
        this.workerService = workerService;
        this.newWorkerValidator = newWorkerValidator;
        this.updWorkerValidator = updWorkerValidator;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<WorkerDTO>> getAllWorkers(
            @RequestParam(name = "size", required = false)
            @DecimalMin(value = "1", message = "page size should be number")
                    String size,
            @RequestParam(name = "page", required = false)
            @DecimalMin(value = "0", message = "page number should be not-negative")
                    String page,
            @RequestParam(name = "filter", required = false)
                    String filter,
            @RequestParam(name = "sort", required = false)
                    String sort) {

        Map<String, String> allRequestParams = new HashMap<>();

        if (size != null) allRequestParams.put("size", size);
        else allRequestParams.put("size", String.valueOf(10));
        if (page != null) allRequestParams.put("page", page);
        else allRequestParams.put("page", String.valueOf(0));
        if (filter != null) allRequestParams.put("filter", filter);
        if (sort != null) allRequestParams.put("sort", sort);

        return workerService.getAllWorkers(allRequestParams).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @CrossOrigin
    @GetMapping("{id}")
    public ResponseEntity<List<WorkerDTO>> getWorker(@PathVariable("id") Integer id) {
        return workerService.getWorker(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @CrossOrigin
    @PostMapping
    public ResponseEntity<WorkerDTO> addNewWorker(@Valid NewWorkerReqParam newWorkerReqParam, BindingResult bindingResult) {
        newWorkerValidator.validate(newWorkerReqParam, bindingResult);
        return workerService.addNewWorker(newWorkerReqParam).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @CrossOrigin
    @PutMapping
    public ResponseEntity<WorkerDTO> updateWorker(@Valid UPDWorkerParam updWorkerParam, BindingResult bindingResult) {
        updWorkerValidator.validate(updWorkerParam, bindingResult);
        return workerService.updateWorker(updWorkerParam).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable("id")
                                               @DecimalMin(value = "1", message = "the id should be positive") String id) {

        return workerService.deleteWorker(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
