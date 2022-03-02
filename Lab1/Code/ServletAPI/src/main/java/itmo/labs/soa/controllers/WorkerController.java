package itmo.labs.soa.controllers;

import com.google.gson.Gson;
import itmo.labs.soa.entities.dto.WorkerDTO;
import itmo.labs.soa.service.Impl.WorkerServiceImpl;
import itmo.labs.soa.service.WorkerService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WorkerController extends HttpServlet {

    Gson gson = new Gson();
    WorkerService workerService = new WorkerServiceImpl();


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<WorkerDTO> dtos = new ArrayList<>();
        

        if (request.getParameterMap().size() == 0){
            dtos = workerService.getAll();
        }
        else if (request.getParameterMap().size() == 1 && request.getParameter("id") != null)
            dtos.add(workerService.getById(request.getParameter("id").replace("/", "")));

        else
            dtos = workerService.getModifiedList(request.getParameterMap());


        sendAsJson(response, dtos);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WorkerDTO workerDTO = workerService.addNewWorker(request.getParameterMap());
        sendAsJson(response, workerDTO);
    }



    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WorkerDTO dto = workerService.update(request.getParameterMap());
        sendAsJson(response, "success");
    }


    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        workerService.delete(request.getParameter("id"));
        sendAsJson(response, "success");

    }


    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {

        response.setContentType("application/json");
        setAccessControlHeaders(response);
        String res = gson.toJson(obj);
        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods",  "DELETE");
    }

}
