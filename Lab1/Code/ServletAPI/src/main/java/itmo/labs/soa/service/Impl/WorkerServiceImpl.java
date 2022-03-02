package itmo.labs.soa.service.Impl;

import com.sun.istack.NotNull;
import itmo.labs.soa.entities.Coordinates;
import itmo.labs.soa.entities.Organization;
import itmo.labs.soa.entities.Worker;
import itmo.labs.soa.entities.dto.WorkerDTO;
import itmo.labs.soa.entities.enums.Position;
import itmo.labs.soa.entities.enums.Status;
import itmo.labs.soa.exception.NoSuchWorkerException;
import itmo.labs.soa.exception.ParamNotFound;
import itmo.labs.soa.repository.CrudRepository;
import itmo.labs.soa.repository.Impl.CrudRepositoryImpl;
import itmo.labs.soa.service.WorkerService;
import itmo.labs.soa.utils.Validation;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class WorkerServiceImpl implements WorkerService {

    private  static List<String> paramNamesList = Arrays.asList("name", "y-coord", "startDate","salary","position","status", "organizationId");
    private final static String xCoordParamName = "x-coord";
    private final static String yCoordParamName = "y-coord";
    private final static String startDateParamName = "startDate";
    private final static String salaryParamName = "salary";
    private final static String positionParamName = "position";
    private final static String statusParamName = "status";
    private final static String organizationIdParamName = "organizationId";
    boolean isAllParams = true;
    String notFoundedParamName;


    Validation validation = new Validation();
    CrudRepository<Organization> organizationRepository = new CrudRepositoryImpl<>(Organization.class);
    CrudRepository<Worker> workerRepository = new CrudRepositoryImpl<>(Worker.class);
    CrudRepository<Coordinates> coordinatesRepository = new CrudRepositoryImpl<>(Coordinates.class);


    @Override
    public WorkerDTO addNewWorker(Map<String, String[]> params, Worker worker) throws Exception {

        paramNamesList.forEach(paramName ->{
            if (!params.containsKey(paramName)) {
                this.isAllParams = false;
                this.notFoundedParamName = paramName;
            }
        });

        if (!isAllParams) throw new ParamNotFound(notFoundedParamName);

        String name = params.get("name")[0];

        double xCoord = validation.checkCoord(validation.checkDouble(params.get(xCoordParamName)[0], xCoordParamName), xCoordParamName, 271d);

        Double yCoord = validation.checkCoord(validation.checkDouble(params.get(yCoordParamName)[0], yCoordParamName),yCoordParamName, 57d);
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime startDate = validation.handleDateTime(validation.normalizeData(params.get(startDateParamName)[0]), startDateParamName);
        Double salary = validation.checkSalary(params.get(salaryParamName)[0], salaryParamName);
        Position position = validation.handlePosition(params.get(positionParamName)[0], positionParamName);
        Status status= validation.handleStatus(params.get(statusParamName)[0], statusParamName);
        Integer organizationId = validation.checkInteger(params.get(organizationIdParamName)[0], organizationIdParamName);

        Coordinates coordinates = new Coordinates(xCoord, yCoord);
        Organization organization = organizationRepository.findById(organizationId).get();

        coordinatesRepository.save(coordinates);

        Worker newWorker;

       if (worker != null)  {
           newWorker = worker;
           newWorker.setName(name);
           newWorker.setCreationDate(creationDate);
           newWorker.setSalary(salary);
           newWorker.setStartDate(startDate);
           newWorker.setCoordinates(coordinates);
           newWorker.setStatus(status);
           newWorker.setPosition(position);
           newWorker.setOrganization(organization);
       }
        else newWorker = new Worker(name, coordinates, creationDate, salary, startDate, position, status, organization);

        workerRepository.save(newWorker);
        return (WorkerDTO) newWorker.mapToDto();
    }

    public WorkerDTO addNewWorker(Map<String, String[]> params) throws Exception {
        return addNewWorker(params, null);
    }

    @Override
    public WorkerDTO getById(String id) throws Exception {

        Integer workerId = validation.checkId(id);
        Optional<Worker> worker;
        try {
            worker = workerRepository.findById(workerId);
        }catch (Exception ex){
            throw new NoSuchWorkerException(id);
        }
        return(WorkerDTO) worker.get().mapToDto();
    }

    @Override
    public WorkerDTO update(Map<String, String[]> params) throws Exception {
        Worker worker = workerRepository.findById(validation.checkId(params.get("id")[0])).get();
        return addNewWorker(params, worker);
    }

    @Override
    public void delete(String workerId) throws Exception {
        workerRepository.deleteById(getById(workerId).getId());
    }

    @Override
    public List<WorkerDTO> getAll() {
        return workerRepository.getAll().stream()
                .map(worker -> (WorkerDTO )worker.mapToDto()).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public List<WorkerDTO> getModifiedList(Map<String, String[]> parameterMap) {
        int pageNumber;
        int itemsPerPage;
        if (parameterMap.containsKey("size"))
            itemsPerPage = validation.checkInteger(parameterMap.get("size")[0], "");
        else itemsPerPage = 10;
        if(parameterMap.containsKey("page"))
            pageNumber = validation.checkPage(parameterMap.get("page")[0]);
        else pageNumber = 0;



        List<String> filterParts = new ArrayList<>();
        if (parameterMap.containsKey("filter")) {
            if (parameterMap.get("filter")[0].contains(";")) {
                String[] strs = parameterMap.get("filter")[0].split(";");
                for (int i = 0; i < strs.length; i++) {
                    System.out.println(strs[i].contains("name="));
                    if (strs[i].contains("name=")){
                        StringBuffer sb = new StringBuffer(strs[i]);
                        sb.insert(5,"'");
                        strs[i] = sb.toString()+"'";
                    }
                    System.out.println(strs[i]);
                }
                filterParts = Arrays.asList(strs);
            }
            else {
                String param = parameterMap.get("filter")[0];
                if (param.contains("name=")) {
                    StringBuffer sb = new StringBuffer(param);
                    sb.insert(5, "'");
                    param = sb.toString() + "'";
                }
                filterParts.add(param);
            }
        }

        List<String> sortParts = new ArrayList<>();
        if (parameterMap.containsKey("sort")) {
            sortParts = Arrays.asList(parameterMap.get("sort")[0].split(";"));
        }

        return workerRepository.getWithSortAndFilter(filterParts, sortParts, itemsPerPage, pageNumber).stream()
                .map(worker -> (WorkerDTO )worker.mapToDto()).collect(Collectors.toList());
    }

    @Override
    public List<WorkerDTO> getByMaxPosition(Map<String, String[]> parameterMap) {

        List<String> filterParams = Collections.singletonList("position='3'");
//        List<WorkerDTO> result
        return workerRepository.getWithSortAndFilter(filterParams, null, 10, 0).stream()
                .map(worker -> (WorkerDTO )worker.mapToDto()).collect(Collectors.toList());


    }
}
