package itmo.labs.soa.controllers;

import com.google.gson.Gson;
import itmo.labs.soa.entities.Coordinates;
import itmo.labs.soa.entities.Location;
import itmo.labs.soa.entities.Worker;
import itmo.labs.soa.entities.dto.WorkerDTO;
import itmo.labs.soa.repository.CrudRepository;
import itmo.labs.soa.repository.Impl.CrudRepositoryImpl;
import itmo.labs.soa.service.Impl.WorkerServiceImpl;
import itmo.labs.soa.service.WorkerService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MaxPositionController extends HttpServlet {

    CrudRepositoryImpl<Location> repository = new CrudRepositoryImpl<>(Location.class);
    Gson gson = new Gson();
    CrudRepository<Worker> workerRepository = new CrudRepositoryImpl<>(Worker.class);
    CrudRepository<Coordinates> coordinatesRepository = new CrudRepositoryImpl<>(Coordinates.class);
    WorkerService workerService = new WorkerServiceImpl();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<WorkerDTO> dtos = workerService.getByMaxPosition(request.getParameterMap());
//        if (dtos!)
        sendAsJson(response,dtos);
    }

    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {

        response.setContentType("application/json");
        String res = gson.toJson(obj);
        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }
}
