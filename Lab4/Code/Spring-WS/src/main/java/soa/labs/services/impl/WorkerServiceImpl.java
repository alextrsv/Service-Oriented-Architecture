package soa.labs.services.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import soa.labs.entities.Coordinates;
import soa.labs.entities.Organization;
import soa.labs.entities.Worker;
import soa.labs.entities.dto.*;
import soa.labs.entities.enums.*;
import soa.labs.exception.*;
import soa.labs.repositories.*;
import soa.labs.services.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkerServiceImpl implements WorkerService {

    final WorkerRepository workerRepository;

    final OrganizationRepository organizationRepository;

    final CoordinatesRepository coordinatesRepository;

    public WorkerServiceImpl(WorkerRepository workerRepository, OrganizationRepository organizationRepository, CoordinatesRepository coordinatesRepository) {
        this.workerRepository = workerRepository;
        this.organizationRepository = organizationRepository;
        this.coordinatesRepository = coordinatesRepository;
    }

    @SneakyThrows
    @Override
    public Optional<List<WorkerDTO>> getAllWorkers(Map<String, String> params) {

//        if (params.isEmpty())
        if (params.get("size").equals("")
                && params.get("page").equals("")
                && params.get("sort").equals("")
                && params.get("filter").equals("")  )
            return Optional.of(workerRepository.findAll().stream()
                .map(worker -> (WorkerDTO) worker.mapToDto()).collect(Collectors.toList()));
        else {
            int pageNumber = Integer.parseInt(params.get("page"));
            int itemsPerPage = Integer.parseInt(params.get("size"));

            List<String> filterParts = getFilterParams(params);

            List<String> sortParts = new ArrayList<>();
            if (params.containsKey("sort")) {
                sortParts = Arrays.asList(params.get("sort").split(";"));
            }

            return Optional.of(workerRepository.getWithSortAndFilter(filterParts, sortParts, itemsPerPage, pageNumber).stream()
                    .map(worker -> (WorkerDTO) worker.mapToDto()).collect(Collectors.toList()));
        }
    }


    private List<String> getFilterParams(Map<String, String> params) {
        List<String> filterParts = new ArrayList<>();
        if (params.containsKey("filter")) {
            if (params.get("filter").contains(";")) {
                String[] strs = params.get("filter").split(";");
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i].contains("name=")) {
                        StringBuffer sb = new StringBuffer(strs[i]);
                        sb.insert(5, "'");
                        strs[i] = sb.toString() + "'";
                    }
                }
                filterParts = Arrays.asList(strs);
            } else {
                String param = params.get("filter");
                if (param.contains("name=")) {
                    StringBuffer sb = new StringBuffer(param);
                    sb.insert(5, "'");
                    param = sb.toString() + "'";
                }
                filterParts.add(param);
            }
        }
        return filterParts;
    }

    @Override
    public Optional<List<WorkerDTO>> getWorker(int id) {

        Optional<Worker> workerWrapper = workerRepository.findById(id);
        if (workerWrapper.isEmpty()) return Optional.empty();
        else {
            List<WorkerDTO> result = new ArrayList<>();
            result.add((WorkerDTO) workerWrapper.get().mapToDto());
            return Optional.of(result);
        }

    }

    @SneakyThrows
    @Override
    public Optional<WorkerDTO> addNewWorker(NewWorkerReqParam newWorkerReqParam) {

        LocalDate creationDate = LocalDate.now();
        Coordinates coordinates = new Coordinates(Double.parseDouble(newWorkerReqParam.getXCoord()), Double.parseDouble(newWorkerReqParam.getYCoord()));
        Organization organization = organizationRepository.findById(Integer.parseInt(newWorkerReqParam.getOrganizationId())).get();

        coordinatesRepository.save(coordinates);

        Worker newWorker = new Worker(newWorkerReqParam.getName(), coordinates, creationDate, Double.parseDouble(newWorkerReqParam.getSalary()),
                LocalDate.parse(newWorkerReqParam.getStartDate()), Position.valueOf(newWorkerReqParam.getPosition()),
                Status.valueOf(newWorkerReqParam.getStatus()), organization);

        workerRepository.save(newWorker);
        return Optional.ofNullable((WorkerDTO) newWorker.mapToDto());
    }

    @SneakyThrows
    @Override
    public Optional<WorkerDTO> updateWorker(UPDWorkerParam dto) {
        Optional<Worker> workerWrapper;
        Worker worker = null;
        if (dto.getId() != null && !dto.getId().equals("")) {
            int id = Integer.parseInt(dto.getId());
            workerWrapper = workerRepository.findById(id);
            if (workerWrapper.isPresent()) {
                worker = workerWrapper.get();
                Coordinates coordinates = new Coordinates();

                if ((dto.getXCoord() != null && !dto.getXCoord().equals("")) &&
                        (dto.getYCoord() != null && !dto.getYCoord().equals(""))) {
                    Optional<Coordinates> coordinatesWrapper =
                            coordinatesRepository.findByXAndY(Double.parseDouble(dto.getXCoord()),
                                    Double.parseDouble(dto.getYCoord()));
                    coordinates = coordinatesWrapper.orElseGet(() -> new Coordinates(Double.parseDouble(dto.getXCoord()), Double.parseDouble(dto.getYCoord())));

                    worker.setCoordinates(coordinates);
                }
                Organization organization = null;

                if (dto.getOrganizationId() != null && !dto.getOrganizationId().equals("")) {
                    organization = organizationRepository.findById(Integer.valueOf(dto.getOrganizationId())).get();
                    worker.setOrganization(organization);
                }


                if (dto.getName() != null && !dto.getName().equals("")) worker.setName(dto.getName());
                if (dto.getSalary() != null && !dto.getSalary().equals("")) worker.setSalary(Double.valueOf(dto.getSalary()));
                if (dto.getStartDate() != null && !dto.getStartDate().equals("")) worker.setStartDate(LocalDate.parse(dto.getStartDate()));
                if (dto.getStatus() != null && !dto.getStatus().equals("")) worker.setStatus(Status.valueOf(dto.getStatus()));
                if (dto.getPosition() != null && !dto.getPosition().equals("")) worker.setPosition(Position.valueOf(dto.getPosition()));

                workerRepository.save(worker);
            } else {
                throw new NoSuchWorkerException(String.valueOf(id));
            }
        } else {
            throw new ParamNotFound("id");
        }
        return Optional.of((WorkerDTO) worker.mapToDto());
    }

    @Override
    public String deleteWorker(String id) {
        try {
            workerRepository.deleteById(Integer.parseInt(id));
            return "success";
        } catch (Exception exception) {
            return "some error";
        }
    }
}
